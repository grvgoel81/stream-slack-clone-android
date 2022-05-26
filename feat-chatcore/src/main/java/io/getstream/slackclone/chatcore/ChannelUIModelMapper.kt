package io.getstream.slackclone.chatcore

import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import javax.inject.Inject

class ChannelUIModelMapper @Inject constructor() :
  UiModelMapper<DomainLayerChannels.SlackChannel, UiLayerChannels.SlackChannel> {
  override fun mapToPresentation(model: DomainLayerChannels.SlackChannel): UiLayerChannels.SlackChannel {
    return UiLayerChannels.SlackChannel(
      model.name,
      model.isPrivate,
      model.uuid,
      model.createdDate,
      model.modifiedDate,
      model.isMuted,
      model.isOneToOne,
      model.avatarUrl
    )
  }

  override fun mapToDomain(modelItem: UiLayerChannels.SlackChannel): DomainLayerChannels.SlackChannel {
    return DomainLayerChannels.SlackChannel(
      modelItem.uuid,
      modelItem.name,
      modelItem.createdDate,
      modelItem.modifiedDate,
      modelItem.isMuted,
      modelItem.isPrivate,
      false,
      false,
      modelItem.isOneToOne,
      modelItem.pictureUrl
    )
  }
}
