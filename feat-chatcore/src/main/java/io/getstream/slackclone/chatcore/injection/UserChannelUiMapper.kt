package io.getstream.slackclone.chatcore.injection

import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import javax.inject.Inject

class UserChannelUiMapper @Inject constructor() :
  UiModelMapper<DomainLayerUsers.SlackUser, UiLayerChannels.SlackChannel> {
  override fun mapToPresentation(model: DomainLayerUsers.SlackUser): UiLayerChannels.SlackChannel {
    TODO("Not yet implemented")
  }

  override fun mapToDomain(modelItem: UiLayerChannels.SlackChannel): DomainLayerUsers.SlackUser {
    TODO("Not yet implemented")
  }
}
