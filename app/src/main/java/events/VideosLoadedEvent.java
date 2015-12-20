package events;

import com.xoredge.tariqjameelbayans.models.Video;

import java.util.List;

/**
 * Created by Ahmad Ali on 11/15/2015.
 */
public class VideosLoadedEvent {
    public List<Video> videosList;
    public String dueto;

    public VideosLoadedEvent(List<Video> videosList){
        this.videosList = videosList;
    }

    public VideosLoadedEvent() {

    }
}
