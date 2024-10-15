package io.getstream.slackclone.uidashboard.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.commonui.material.SlackSurfaceAppBar
import io.getstream.slackclone.commonui.reusable.SlackImageBox
import io.getstream.slackclone.commonui.reusable.SlackListItem
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.uichannels.views.SlackAllChannels
import io.getstream.slackclone.uichannels.views.SlackConnections
import io.getstream.slackclone.uichannels.views.SlackDirectMessages
import io.getstream.slackclone.uichannels.views.SlackRecentChannels
import io.getstream.slackclone.uichannels.views.SlackStarredChannels
import io.getstream.slackclone.uidashboard.R

@Composable
fun HomeScreenUI(
  appBarIconClick: () -> Unit,
  onItemClick: (UiLayerChannels.SlackChannel) -> Unit = {},
  onCreateChannelRequest: () -> Unit = {}
) {
  SlackCloneSurface(
    color = SlackCloneColorProvider.colors.uiBackground,
    modifier = Modifier.fillMaxSize()
  ) {
    Column() {
      SlackMMTopAppBar(appBarIconClick)
      Column(Modifier.verticalScroll(rememberScrollState())) {
        JumpToText()
        ThreadsTile()
        Divider(color = SlackCloneColorProvider.colors.lineColor, thickness = 0.5.dp)
        SlackRecentChannels({
          onItemClick(it)
        }, onClickAdd = {
          onCreateChannelRequest()
        })
        SlackStarredChannels({
          onItemClick(it)
        }, onClickAdd = {
          onCreateChannelRequest()
        })
        SlackDirectMessages({
          onItemClick(it)
        }, onClickAdd = {
          onCreateChannelRequest()
        })
        SlackAllChannels({
          onItemClick(it)
        }, onClickAdd = {
          onCreateChannelRequest()
        })
        SlackConnections({
          onItemClick(it)
        }, onClickAdd = {
          onCreateChannelRequest()
        })
      }
    }
  }
}

@Composable
fun ThreadsTile() {
  SlackListItem(
    Icons.Default.MailOutline,
    stringResource(io.getstream.slackclone.common.R.string.threads)
  )
}

@Composable
fun JumpToText() {
  Box(
      Modifier
          .fillMaxWidth()
          .clickable { }
          .padding(12.dp)
  ) {
    RoundedCornerBoxDecoration {
      Text(
        text = "Jump to...",
        style = SlackCloneTypography.subtitle2.copy(color = SlackCloneColorProvider.colors.textPrimary),
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun SlackMMTopAppBar(appBarIconClick: () -> Unit) {
  SlackSurfaceAppBar(
    title = {
      Text(text = "Stream", style = SlackCloneTypography.h5.copy(color = Color.White))
    },
    navigationIcon = {
      MMImageButton(appBarIconClick)
    },
    backgroundColor = SlackCloneColorProvider.colors.appBarColor,
  )
}

@Composable
fun MMImageButton(appBarIconClick: () -> Unit) {
  IconButton(onClick = {
    appBarIconClick()
  }) {
    SlackImageBox(
      Modifier.size(38.dp),
      io.getstream.slackclone.commonui.R.drawable.logo_stream
    )
  }
}
