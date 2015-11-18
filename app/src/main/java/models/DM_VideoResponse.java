package models;

import java.util.List;

/**
 * Created by Ahmad Ali on 11/16/2015.
 */
public class DM_VideoResponse {
    public int page;
    public int limit;
    public boolean explicit;
    public boolean has_more;
    public List<Video> list;
}
