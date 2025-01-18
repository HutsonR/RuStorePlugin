package com.blackcube.rustorepublisher.network.models.responses

/**
 * Response parameters for the method to retrieve a list of available applications.
 *
 * @property code Response code indicating the result of the request.
 * **Example**: `"error"` or `"OK"`
 * @property message Decryption of the response code, in case of an error.
 * @property body see [AppsResponseBody]
 * @property timestamp Timestamp of the response in ISO 8601 format.
 * **Example**: `"2022-07-08T13:24:41.8328711+03:00"`
 */
data class AppsResponse(
    val code: String,
    val message: String?,
    val body: AppsResponseBody,
    val timestamp: String
)

/**
 * Represents the body of the response.
 *
 * @property content Array containing the list of available applications.
 * @property pageSize Number of applications on the current page.
 * **Example**: `"20"`
 * @property continuationToken Continuation token for the next page if the application list spans multiple pages.
 * **Example**: `"Nzk0MjQ3Mzcw"`
 */
data class AppsResponseBody(
    val content: List<AppsResponseApplication>,
    val pageSize: String,
    val continuationToken: String?
)

/**
 * Represents an application in the response content array.
 *
 * @property activePrice Price of the application.
 * **Example**: `120.0`
 * @property appId Unique identifier of the application.
 * **Example**: `478564`
 * @property appName Name of the application.
 * **Example**: `"Application1"`
 * @property appStatus Status of the application. Possible values:
 * - `ACTIVE` – version is published.
 * - `PARTIAL_ACTIVE` – version is published for a percentage of users.
 * - `READY_FOR_PUBLICATION` – version is approved by the moderator.
 * - `PREVIOUS_ACTIVE` – previous active version.
 * - `ARCHIVED` – version is archived.
 * - `REJECTED_BY_MODERATOR` – version is rejected by the moderator.
 * - `TAKEN_FOR_MODERATION` – version is under moderation.
 * - `MODERATION` – awaiting moderator actions.
 * - `AUTO_CHECK` – undergoing Kaspersky antivirus check.
 * - `AUTO_CHECK_FAILED` – failed antivirus check.
 * - `DRAFT` – draft version.
 * - `DELETED_DRAFT` – deleted draft version.
 * - `REJECTED_BY_SECURITY` – rejected due to security concerns.
 * **Example**: `"ACTIVE"`
 * @property appVerUpdatedAt Date and time of the version update in ISO 8601 format.
 * **Example**: `"2024-03-11T12:23:55.596998+03:00"`
 * @property companyName Name of the company owning the application.
 * **Example**: `"Example Company"`
 * @property deviceType Type of device for the application.
 * **Example**: `"MOBILE"`
 * @property iconUrl URL of the application icon.
 * **Example**: `"https://example.com/icon.png"`
 * @property packageName Package name of the application.
 * **Example**: `"com.myapp.example"`
 * @property paid Indicates whether the application is paid.
 * **Example**: `true`
 * @property shortDescription Short description of the application.
 * **Example**: `"Short description of the app"`
 * @property versionCode Code of the application version.
 * **Example**: `1`
 * @property versionName Name of the application version.
 * **Example**: `"1.0"`
 */
data class AppsResponseApplication(
    val activePrice: Int?,
    val appId: Int,
    val appName: String,
    val appStatus: String?,
    val appVerUpdatedAt: String?,
    val companyName: String,
    val deviceType: String?,
    val iconUrl: String?,
    val packageName: String,
    val paid: Boolean = false,
    val shortDescription: String?,
    val versionCode: Int?,
    val versionName: String?
)