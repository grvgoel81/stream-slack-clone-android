package io.getstream.slackclone.uidashboard.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.getstream.slackclone.commonui.material.SlackSurfaceAppBar
import io.getstream.slackclone.commonui.reusable.SlackImageBox
import io.getstream.slackclone.commonui.reusable.SlackListItem
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.commonui.theme.slackFontFamily
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackScreen
import io.getstream.slackclone.uidashboard.R

@Composable
fun SideNavigation(
  modifier: Modifier,
  dashboardVM: DashboardVM,
  composeNavigator: ComposeNavigator
) {
  SlackCloneSurface(
    color = SlackCloneColorProvider.colors.uiBackground,
    modifier = modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxHeight(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        WorkspacesBar()
        Spacer(modifier = Modifier.padding(8.dp))
        Workspace(selected = true, imageModel = R.drawable.logo_stream, workSpace = "Stream")
        Spacer(modifier = Modifier.padding(8.dp))
        Workspace(
          selected = false,
          imageModel = R.drawable.logo_gdg,
          workSpace = "GDG"
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Workspace(selected = false, imageModel = R.drawable.logo_compose, workSpace = "Compose")
        Spacer(modifier = Modifier.padding(8.dp))
        Workspace(selected = false, imageModel = R.drawable.logo_kotlin, workSpace = "Kotlin")
        Spacer(modifier = Modifier.padding(8.dp))
        Workspace(selected = false, imageModel = R.drawable.logo_flutter, workSpace = "Flutter")
      }
      Spacer(modifier = Modifier.padding(8.dp))
      SideNavFooter(
        dashboardVM = dashboardVM,
        composeNavigator = composeNavigator
      )
    }
  }
}

@Composable
private fun SideNavFooter(dashboardVM: DashboardVM, composeNavigator: ComposeNavigator) {
  Column(modifier = Modifier.navigationBarsPadding()) {
    Divider(color = SlackCloneColorProvider.colors.lineColor)
    SlackListItem(Icons.Filled.AddCircle, stringResource(id = R.string.add_workspace))
    SlackListItem(Icons.Filled.Settings, stringResource(id = R.string.preferences))
    SlackListItem(Icons.Filled.Close, stringResource(id = R.string.logout), onItemClick = {
      dashboardVM.logout()
      composeNavigator.navigate(SlackScreen.GettingStarted.route)
    })
  }
}

@Composable
private fun Workspace(selected: Boolean, imageModel: Any, workSpace: String) {
  Box(
    Modifier.background(
      color = if (selected) SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.2f) else Color.Transparent,
      shape = RoundedCornerShape(12.dp)
    )
  ) {
    Row(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      OrganizationLogo(imageModel)
      Box(Modifier.weight(1f)) {
        OrganizationDetails(workSpace)
      }
      Icon(
        imageVector = Icons.Filled.MoreVert,
        contentDescription = null,
        tint = SlackCloneColorProvider.colors.textPrimary
      )
    }
  }
}

@Composable
private fun OrganizationDetails(workSpace: String) {
  Column(
    modifier = Modifier
      .padding(start = 8.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.Start
  ) {
    Text(
      text = workSpace,
      style = SlackCloneTypography.h6.copy(
        color = SlackCloneColorProvider.colors.textPrimary,
        fontWeight = FontWeight.SemiBold
      )
    )
    Text(
      text = "${workSpace.lowercase()}.slack.com",
      style = SlackCloneTypography.subtitle1.copy(
        fontWeight = FontWeight.Normal,
        color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.4f)
      ),
    )
  }
}

@Composable
private fun OrganizationLogo(
  imageModel: Any
) {
  Box(
    Modifier
      .size(68.dp)
      .border(
        width = 3.dp,
        color = SlackCloneColorProvider.colors.textPrimary,
        shape = RoundedCornerShape(12.dp)
      )
      .padding(8.dp)
  ) {
    SlackImageBox(
      Modifier.size(64.dp),
      imageModel
    )
  }
}

@Composable
private fun WorkspacesBar() {
  SlackSurfaceAppBar(
    backgroundColor = SlackCloneColorProvider.colors.uiBackground,
    elevation = 0.dp,
    contentPadding = rememberInsetsPaddingValues(
      insets = LocalWindowInsets.current.statusBars,
      applyStart = true,
      applyTop = true,
      applyEnd = true,
    )
  ) {
    Text(
      text = stringResource(id = R.string.head_workspaces),
      style = SlackCloneTypography.h5.copy(
        color = SlackCloneColorProvider.colors.textPrimary,
        fontFamily = slackFontFamily,
        fontWeight = FontWeight.Bold
      ),
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}
