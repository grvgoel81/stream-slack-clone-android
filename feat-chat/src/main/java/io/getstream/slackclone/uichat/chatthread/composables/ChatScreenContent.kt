package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.slackclone.uichat.chatthread.BoxState
import io.getstream.slackclone.uichat.chatthread.ChatScreenVM

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ChatScreenContent(
  slackChannel: Channel,
  viewModel: ChatScreenVM = hiltViewModel()
) {
  val checkBoxState by viewModel.chatBoxState.collectAsState()
  val manualExpandValue = if (checkBoxState == BoxState.Expanded) {
    1f
  } else {
    0f
  }

  val change by animateFloatAsState(
    manualExpandValue,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioLowBouncy,
      stiffness = Spring.StiffnessMediumLow
    )
  )

  val factory = MessagesViewModelFactory(
    context = LocalContext.current,
    channelId = slackChannel.cid,
  )

  var listViewModel by remember { mutableStateOf<MessageListViewModel?>(null) }
  LaunchedEffect(key1 = slackChannel) {
    listViewModel = factory.create(MessageListViewModel::class.java)
  }

  listViewModel?.let {
    val currentState = it.currentMessagesState

    MotionLayout(
      start = chatConstrains(),
      end = chatConstrainsExpanded(),
      progress = change,
      modifier = Modifier
        .navigationBarsWithImePadding()
        .fillMaxHeight()
        .fillMaxWidth()
    ) {
      ChatMessagesUI(
        modifier = Modifier.layoutId("chatView"),
        currentState = currentState,
        listViewModel = it
      )
      ChatMessageBox(
        viewModel,
        slackChannel = slackChannel,
        Modifier
          .layoutId("chatBox")
          .animateDrag(
            onExpand = { viewModel.chatBoxState.value = BoxState.Expanded },
            onCollapse = { viewModel.chatBoxState.value = BoxState.Collapsed }
          )
      )
    }
  }
}

private fun Modifier.animateDrag(
  onExpand: () -> Unit,
  onCollapse: () -> Unit
): Modifier =
  composed {
    val sensitivity = 200
    var swipeOffset by remember {
      mutableStateOf(0f)
    }
    var gestureConsumed by remember {
      mutableStateOf(false)
    }
    this.pointerInput(Unit) {
      detectVerticalDragGestures(
        onVerticalDrag = { _, dragAmount ->
          // dragAmount: positive when scrolling down; negative when scrolling up
          swipeOffset += dragAmount
          when {
            swipeOffset > sensitivity -> {
              // offset > 0 when swipe down
              if (!gestureConsumed) {
                onCollapse()
                gestureConsumed = true
              }
            }

            swipeOffset < -sensitivity -> {
              // offset < 0 when swipe up
              if (!gestureConsumed) {
                onExpand()
                gestureConsumed = true
              }
            }
          }
        }, onDragEnd = {
        swipeOffset = 0f
        gestureConsumed = false
      }
      )
    }
  }

private fun chatConstrainsExpanded(): ConstraintSet {
  return ConstraintSet {
    val chatBox = createRefFor("chatBox")
    val chatView = createRefFor("chatView")
    constrain(chatView) {
      top.linkTo(parent.top)
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(parent.bottom)
    }
    constrain(chatBox) {
      height = Dimension.fillToConstraints
      top.linkTo(parent.top)
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(parent.bottom)
    }
  }
}

private fun chatConstrains(): ConstraintSet {
  return ConstraintSet {
    val chatBox = createRefFor("chatBox")
    val chatView = createRefFor("chatView")
    constrain(chatView) {
      height = Dimension.fillToConstraints
      width = Dimension.fillToConstraints
      top.linkTo(parent.top)
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(chatBox.top)
    }
    constrain(chatBox) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(parent.bottom)
    }
  }
}
