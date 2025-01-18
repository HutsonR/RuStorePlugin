package com.blackcube.rustorepublisher.network.api

import com.blackcube.rustorepublisher.network.models.requests.AuthRequest
import com.blackcube.rustorepublisher.network.models.requests.DraftRequest
import com.blackcube.rustorepublisher.network.models.responses.AppsResponse
import com.blackcube.rustorepublisher.network.models.responses.AuthResponse
import com.blackcube.rustorepublisher.network.models.responses.DraftResponse
import com.blackcube.rustorepublisher.network.models.responses.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RuStoreApi {

    /**
     *  Api method for auth requests.
     *
     *  @param authRequest Signature for auth.
     *
     *  @return [AuthResponse]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-authorization-token).
     * */
    @POST("auth")
    fun auth(@Body authRequest: AuthRequest): Call<AuthResponse>

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
     * @return [AppsResponse]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/get-app-list).
     * */
    @GET("v1/application")
    fun getApps(
        @Header("Public-Token") token: String,
        @Path("pagination") pagination: Boolean = false,
        @Path("continuationToken") continuationToken: String?,
        @Path("pageSize") pageSize: String?,
        @Path("appName") appName: String?,
        @Path("appPackage") appPackage: String?,
        @Path("orderFields") orderFields: String?,
        @Path("sortDirections") sortDirections: String?
    ): Call<AppsResponse>

    /**
     * Api method for create draft.
     *
     * @param token Access token to the Rustore Public API.
     * @param packageName The name of the application package.
     * @param draftRequest See [DraftRequest].
     *
     * @return [DraftResponse]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/create-draft-version).
     * */
    @POST("v1/application/{packageName}/version")
    fun createDraft(
        @Header("Public-Token") token: String,
        @Path("packageName") packageName: String,
        @Body draftRequest: DraftRequest
    ): Call<DraftResponse>

    /**
     * Api method for upload APK.
     *
     * @param token Access token to the Rustore Public API.
     * @param packageName The name of the application package.
     * @param versionId Application Version.
     * @param servicesType The type of service used in the application. Possible options:
     *
     * • HMS — for APK files from Huawei Mobile Services;
     *
     * • Unknown is set by default if the field is not filled in
     * @param isMainApk The attribute that is assigned to the main APK file. Values:
     *
     * • true — the main APK file;
     *
     * • false — by default.
     * @param file The application file is in the form of binary code.
     *
     * @return [UploadFileResponse]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/apk-file-upload/file-upload-apk).
     * */
    @Multipart
    @POST("v1/application/{packageName}/version/{versionId}/apk")
    fun uploadApk(
        @Header("Public-Token") token: String,
        @Path("packageName") packageName: String,
        @Path("versionId") versionId: Int,
        @Query("servicesType") servicesType: String,
        @Query("isMainApk") isMainApk: Boolean,
        @Part file: MultipartBody.Part
    ): Call<UploadFileResponse>

    /**
     * Api method for upload AAB.
     *
     * @param token Access token to the Rustore Public API.
     * @param packageName The name of the application package.
     * @param versionId Application Version.
     * @param file The application file is in the form of binary code.
     *
     * @return [UploadFileResponse]
     *
     * More docs are available
     * [here](https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/apk-file-upload/file-upload-aab).
     * */
    @Multipart
    @POST("v1/application/{packageName}/version/{versionId}/aab")
    fun uploadAab(
        @Header("Public-Token") token: String,
        @Path("packageName") packageName: String,
        @Path("versionId") versionId: Int,
        @Part file: MultipartBody.Part
    ): Call<UploadFileResponse>



    @Multipart
    @POST("v1/application/{packageName}/version/{versionId}/image/icon")
    fun uploadIcon(
        @Header("Public-Token") token: String,
        @Path("packageName") packageName: String,
        @Path("versionId") versionId: Int,
        @Part file: MultipartBody.Part
    ): Call<UploadFileResponse>

}