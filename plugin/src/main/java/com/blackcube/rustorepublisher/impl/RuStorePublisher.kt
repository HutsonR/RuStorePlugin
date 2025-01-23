package com.blackcube.rustorepublisher.impl

import com.blackcube.rustorepublisher.impl.models.ServicesType
import com.blackcube.rustorepublisher.network.models.requests.PublishType
import java.io.File

interface RuStorePublisher {

    /**
     *  The method allows you to generate a JWE token using a private key obtained in the Ru Store Console system.
     *
     * @param keyId key id.
     * @param privateKey private key from [Ru Store Console](https://console.rustore.ru/sign-in).
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-authorization-token).
     *
     * */
    fun getToken(
        keyId: String,
        privateKey: String
    ): AuthResult

    /**
     * Api method for getting the list of applications.
     *
     * @param token Access token to the Rustore Public API.
     *
     * @param pagination The flag for enabling pagination.
     * Default is `false`. Set to `true` if pagination is required.
     * **Example**: `true`
     *
     * @param continuationToken Continuation token to fetch additional pages if the list of applications spans multiple pages.
     * This value is returned in the response of the previous request.
     * **Example**: `"Nzk0MjQ3Mzcw"`
     *
     * @param pageSize Number of applications per page. Required if `pagination = true`.
     * **Default**: `20`
     * **Minimum**: `1`, **Maximum**: `1000`
     * **Example**: `50`
     *
     * @param appName Parameter to search for applications by name.
     * **Example**: `"MyApp"`
     *
     * @param appPackage Parameter to search for applications by `package_name`.
     * **Example**: `"com.myapp.example"`
     *
     * @param orderFields Field for sorting. Available values:
     * - `appId` — application ID
     * - `appName` — application name
     * - `appStatus` — application status
     * - `appVerUpdatedAt` — application version update date
     * **Example**: `"appName"`
     *
     * @param sortDirections Sorting direction. Available values:
     * - `ASC` — ascending order
     * - `DESC` — descending order
     * Only used if `orderFields` is specified.
     * **Example**: `"ASC"`
     *
     * @return [AppsResult]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/get-app-list).
     * */
    fun getApps(
        token: String,
        pagination: Boolean = false,
        continuationToken: String?,
        pageSize: String?,
        appName: String?,
        appPackage: String?,
        orderFields: String?,
        sortDirections: String?
    ): AppsResult

    /**
     *  Create a draft version and fill it with basic information.
     *
     * @param token token for access to [Public Api Rustore](https://www.rustore.ru/help/work-with-rustore-api/api-authorization-token).
     * @param packageName the app's package name.
     * @param publishType type of publication.
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/create-draft-version).
     *
     * */
    fun createDraft(
        token: String,
        packageName: String,
        publishType: PublishType
    ): DraftResult

    /**
     *  The method allows you to upload a file with an extension .aab for publication.
     *
     * @param token token for access to [Public Api Rustore](https://www.rustore.ru/help/work-with-rustore-api/api-authorization-token).
     * @param draftId id draft from [createDraft].
     * @param filePath path to AAB file.
     * @param packageName the app's package name.
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/apk-file-upload/file-upload-aab).
     *
     * */
    fun uploadAab(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String
    ): UploadResult

    /**
     *  The method allows you to upload a file with an extension .apk for publication.
     *
     * @param token token for access to [Public Api Rustore](https://www.rustore.ru/help/work-with-rustore-api/api-authorization-token).
     * @param draftId id draft from [createDraft].
     * @param filePath path to APK file.
     * @param packageName the app's package name.
     * @param servicesType see [ServicesType]
     * @param isMainApk The attribute that is assigned to the main APK file. Values:
     *
     * • true — the main APK file;
     *
     * • false — by default.
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/apk-file-upload/file-upload-apk).
     *
     * */
    fun uploadApk(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String,
        servicesType: ServicesType,
        isMainApk: Boolean
    ): UploadResult

    fun uploadIcon(
        token: String,
        draftId: Int,
        filePath: File,
        packageName: String
    ): UploadResult

    /**
     * The method for submitting a draft version of the app for moderation.
     *
     * @param token Access token to the Rustore Public API.
     * @param packageName The name of the application package.
     * @param versionId Application Version.
     * @param priorityUpdate Update priority.
     * Possible values
     * are From 0 to 5, where 0 is the minimum and 5 is the maximum.
     * The default value is 0.
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/send-draft-app-for-moderation).
     * */
    fun submitDraft(
        token: String,
        packageName: String,
        versionId: Int,
        priorityUpdate: Int?
    ): UploadResult
}