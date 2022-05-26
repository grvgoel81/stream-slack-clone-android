package io.getstream.slackclone.uidashboard.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.commonui.material.SlackSurfaceAppBar
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.uichannels.directmessages.DMChannelsList

@Composable
fun DirectMessagesUI(onItemClick: (UiLayerChannels.SlackChannel) -> Unit) {
  SlackCloneSurface(
    color = SlackCloneColorProvider.colors.uiBackground,
    modifier = Modifier.fillMaxSize()
  ) {
    Column {
      DMTopAppBar()
      Spacer(modifier = Modifier.height(8.dp))
      JumpToText()
      Spacer(modifier = Modifier.height(12.dp))
      DMChannelsList(onItemClick)
    }
  }
}

@Composable
fun DMTopAppBar() {
  SlackSurfaceAppBar(
    title = {
      Text(
        text = "Direct Messages",
        style = SlackCloneTypography.h5.copy(color = Color.White, fontWeight = FontWeight.Bold)
      )
    },
    backgroundColor = SlackCloneColorProvider.colors.appBarColor,
  )
}
