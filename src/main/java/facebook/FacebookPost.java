package facebook;

import facebook4j.Place;
import socialmedia.Post;

import java.net.URL;
import java.util.List;

/**
 * The FacebookPost class extends the post class and adds
 * content that only facebook uses for their posts.
 * FacebookPost uses types from facebook4j: Post.Property and Place.
 * @see Post
 * @see Place
 * @see facebook4j.Post.Property
 */
public class FacebookPost extends Post<FacebookUser> {

    private Boolean hidden;
    private Boolean published;
    private URL link;
    private URL source;
    private Place place;
    private String objectId;
    private String parentId;
    private String statusType;
    private Iterable<FacebookUser> withTags;
    private Iterable<FacebookUser> messageTags;
    private List<facebook4j.Post.Property> properties;

    /**
     * Returns users tagged as being 'with' the author of the post.
     * @return  list of users
     */
    public Iterable<FacebookUser> getWithTags() {
        return withTags;
    }

    /**
     * Sets with tags locally.
     * @param withTags  new with tags
     */
    public void setWithTags(Iterable<FacebookUser> withTags) {
        this.withTags = withTags;
    }

    /**
     * Returns the list of users tagged in this post. Users are 'tagged' when their names
     * are written directly in the post text.
     * @return  list of users tagged
     */
    public Iterable<FacebookUser> getMessageTags() {
        return messageTags;
    }

    /**
     * Users which were tagged in the post, by having their names written
     * directly in the post text.
     * @param messageTags   new message tags list
     */
    public void setMessageTags(Iterable<FacebookUser> messageTags) {
        this.messageTags = messageTags;
    }

    /**
     * Returns true if post is marked as hidden. This is
     * only for pages.
     * @return  hidden
     */
    public Boolean isHidden() {
        return hidden;
    }

    /**
     * Sets hidden status for post locally.
     * @param hidden    the new hidden status
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Returns true if post is published. Only applies
     * to scheduled page posts.
     * @return  published
     */
    public Boolean isPublished() {
        return published;
    }

    /**
     * Sets published status locally.
     * @param published new published status
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     * Returns the url of the link attached to the post.
     * @return  url address
     */
    public URL getLink() {
        return link;
    }

    /**
     * Sets the url address locally.
     * @param link  new url link
     */
    public void setLink(URL link) {
        this.link = link;
    }

    /**
     * Returns the id of any uploaded photo or video
     * attached to post.
     * @return  id of object
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the objects id locally.
     * @param objectId  new id of object
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Returns id of parent post, for example if
     * this is a story where 'your page was mentioned', the
     * parent will be the original post where page was mentioned.
     * @return  parent post id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the parent post id locally.
     * @param parentId  new parent post id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Returns information about a location associated
     * with the post.
     * @see Place
     * @return  place information
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Sets place locally.
     * @see Place
     * @param place new place information
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * Returns the url source of any flash movie/video
     * attached to post.
     * @return  url of source
     */
    public URL getSource() {
        return source;
    }

    /**
     * Sets the source locally.
     * @param source    new url source
     */
    public void setSource(URL source) {
        this.source = source;
    }

    /**
     * Returns the type of a status update.
     * (shared_story, added_photos, wall_post...)
     * @return  status type
     */
    public String getStatusType() {
        return statusType;
    }

    /**
     * Sets the status type locally.
     * @param statusType    new status type
     */
    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Override
    public String toString() {
        return "FacebookPost{" +
                super.baseToString() +
                "hidden=" + hidden +
                ", published=" + published +
                ", link=" + link +
                ", source=" + source +
                ", place=" + place +
                ", objectId='" + objectId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", statusType='" + statusType + '\'' +
                ", withTags=" + withTags +
                ", messageTags=" + messageTags +
                ", properties=" + properties +
                '}';
    }
}
