package com.blackcube.rustorepublisher.network.models.requests

/**
 * Request for create draft.
 *
 * @property publishType See [PublishType].
 */
data class DraftRequest(
    val publishType: String
)

/**
 * The method allows you to change the type of publication.
 *
 * @property MANUAL Manual publication
 * @property INSTANTLY Automatic publication immediately after passing moderation
 * @property DELAYED Delayed publication
 *
 * More docs are available
 * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/publishing-change-settings).
 */
enum class PublishType {
    /** Manual publication. */
    MANUAL,

    /** Automatic publication immediately after passing moderation. */
    INSTANTLY,

    /** Delayed publication. */
    DELAYED
}