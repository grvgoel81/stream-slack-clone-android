package io.getstream.slackclone.uichannels.createsearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.getstream.slackclone.chatcore.extensions.toStreamChannel
import io.getstream.slackclone.chatcore.views.SlackChannelItem
import io.getstream.slackclone.commonui.material.SlackSurfaceAppBar
import io.getstream.slackclone.commonui.theme.SlackCloneColor
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTheme
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackScreen

@Composable
fun SearchCreateChannelUI(
  searchChannelsVM: SearchChannelsVM = hiltViewModel(),
  composeNavigator: ComposeNavigator
) {
  SlackCloneTheme {
    val scaffoldState = rememberScaffoldState()

    SlackCloneTheme {
      ListChannels(scaffoldState, composeNavigator, searchChannelsVM = searchChannelsVM) {
        composeNavigator.navigate(SlackScreen.CreateNewChannel.name)
      }
    }
  }
}

@Composable
private fun ListChannels(
  scaffoldState: ScaffoldState,
  composeNavigator: ComposeNavigator,
  searchChannelsVM: SearchChannelsVM,
  newChannel: () -> Unit
) {
  Box {
    Scaffold(
      backgroundColor = SlackCloneColorProvider.colors.uiBackground,
      contentColor = SlackCloneColorProvider.colors.textSecondary,
      modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
      scaffoldState = scaffoldState,
      topBar = {
        val channelCount by searchChannelsVM.channelCount.collectAsState()
        SearchAppBar(composeNavigator, channelCount)
      },
      snackbarHost = {
        scaffoldState.snackbarHostState
      },
      floatingActionButton = {
        NewChannelFAB(newChannel)
      }
    ) { innerPadding ->
      SearchContent(innerPadding, searchChannelsVM)
    }
  }
}

@Composable
private fun SearchContent(innerPadding: PaddingValues, searchChannelsVM: SearchChannelsVM) {
  Box(modifier = Modifier.padding(innerPadding)) {
    SlackCloneSurface(
      modifier = Modifier.fillMaxSize()
    ) {
      Column() {
        SearchChannelsTF(searchChannelsVM)
        ListAllChannels(searchChannelsVM)
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListAllChannels(searchChannelsVM: SearchChannelsVM) {
  val channels by searchChannelsVM.channels.collectAsState()
  val channelsFlow = channels.collectAsLazyPagingItems()
  val listState = rememberLazyListState()
  LazyColumn(state = listState, reverseLayout = false) {
    var lastDrawnChannel: String? = null
    for (channelIndex in 0 until channelsFlow.itemCount) {
      val channel = channelsFlow.peek(channelIndex)!!
      val newDrawn = channel.name?.first().toString()
      if (canDrawHeader(lastDrawnChannel, newDrawn)) {
        stickyHeader {
          SlackChannelHeader(newDrawn)
        }
      }
      item {
        SlackChannelItem(channel.toStreamChannel()) {
          searchChannelsVM.navigate(channel)
        }
      }
      lastDrawnChannel = newDrawn
    }
  }
}

fun canDrawHeader(lastDrawnChannel: String?, name: String?): Boolean {
  return lastDrawnChannel != name
}

@Composable
fun SlackChannelHeader(title: String) {
  Box(
    Modifier
      .fillMaxWidth()
      .background(SlackCloneColorProvider.colors.lineColor)
  ) {
    Text(
      text = title.toUpperCase(Locale.current),
      modifier = Modifier.padding(12.dp),
      style = SlackCloneTypography.subtitle1.copy(color = SlackCloneColorProvider.colors.textSecondary)
    )
  }
}

@Composable
private fun SearchChannelsTF(searchChannelsVM: SearchChannelsVM) {
  val searchChannel by searchChannelsVM.search.collectAsState()

  TextField(
    value = searchChannel,
    onValueChange = { newValue ->
      searchChannelsVM.search(newValue)
    },
    textStyle = textStyleFieldPrimary(),
    placeholder = {
      Text(
        text = "Search for channels",
        style = textStyleFieldSecondary(),
        textAlign = TextAlign.Start
      )
    },
    colors = textFieldColors(),
    singleLine = true,
    maxLines = 1,
  )
}

@Composable
private fun textStyleFieldPrimary() = SlackCloneTypography.subtitle1.copy(
  color = SlackCloneColorProvider.colors.textPrimary,
  fontWeight = FontWeight.Normal,
  textAlign = TextAlign.Start
)

@Composable
private fun textStyleFieldSecondary() = SlackCloneTypography.subtitle1.copy(
  color = SlackCloneColorProvider.colors.textSecondary,
  fontWeight = FontWeight.Normal,
  textAlign = TextAlign.Start
)

@Composable
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
  backgroundColor = Color.Transparent,
  cursorColor = SlackCloneColorProvider.colors.textPrimary,
  unfocusedIndicatorColor = Color.Transparent,
  focusedIndicatorColor = Color.Transparent
)

@Composable
private fun NewChannelFAB(newChannel: () -> Unit) {
  FloatingActionButton(onClick = {
    newChannel()
  }, backgroundColor = Color.White) {
    Icon(
      imageVector = Icons.Default.Add,
      contentDescription = null,
      tint = SlackCloneColor
    )
  }
}

@Composable
private fun SearchAppBar(composeNavigator: ComposeNavigator, count: Int) {
  SlackSurfaceAppBar(
    title = {
      SearchNavTitle(count)
    },
    navigationIcon = {
      NavBackIcon(composeNavigator)
    },
    backgroundColor = SlackCloneColorProvider.colors.appBarColor,
  )
}

@Composable
private fun SearchNavTitle(count: Int) {
  Column {
    Text(
      text = "Channel Browser",
      style = SlackCloneTypography.subtitle1.copy(color = SlackCloneColorProvider.colors.appBarTextTitleColor)
    )
    Text(
      text = "$count channels",
      style = SlackCloneTypography.subtitle2.copy(color = SlackCloneColorProvider.colors.appBarTextSubTitleColor)
    )
  }
}

@Composable
private fun NavBackIcon(composeNavigator: ComposeNavigator) {
  IconButton(onClick = {
    composeNavigator.navigateUp()
  }) {
    Icon(
      imageVector = Icons.Filled.Clear,
      contentDescription = "Clear",
      modifier = Modifier.padding(start = 8.dp),
      tint = SlackCloneColorProvider.colors.appBarIconColor
    )
  }
}
