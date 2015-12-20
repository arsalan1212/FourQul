package network;

import com.xoredge.tariqjameelbayans.models.DM_VideoResponse;
import com.xoredge.tariqjameelbayans.models.Video;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Ahmad Ali on 3/7/2015.
 */
public interface EndPointVideos {

    @GET("/video/{id}")
    Video get(@Path("id") String id);

    @GET("/playlist/{id}/videos")
    Call<DM_VideoResponse> getByPlaylist(@Path("id") String id,@Query("limit") int limit,@Query("page") int page,@Query("fields") String fields);


    @GET("/employeeJobs/0/")
    List<Video> groupList(@Query("group") String group, @Query("filter") String filter, @Query("sort") String sort, @Query("rows") String rows);

    @POST("/employeeJobs/{id}/")
    Video updateJob(@Path("id") long id, @Body Video job);
}
