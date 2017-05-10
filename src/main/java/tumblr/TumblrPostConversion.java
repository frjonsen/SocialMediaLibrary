package tumblr;

import com.tumblr.jumblr.types.*;
import socialmedia.SocialMediaUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static socialmedia.Post.Type.*;
import static tumblr.TumblrUser.UserType.BLOG;
import static tumblr.TumblrUser.UserType.USER;

/**
 * Handles conversion of Jumblr Post to our TumblrPost
 * @see TumblrPost
 * @see Post
 */
public class TumblrPostConversion {
    private TumblrPostConversion(){} //NOSONAR

    /**
     * Takes a Jumblr Post and converts it to a TumblrPost
     * @param jumblrPost post to convert
     * @return TumblrPost created from jumblrPost
     * @throws MalformedURLException gets thrown if the URL in the post is of bad format
     */
    static TumblrPost jumblrPostConversion(Post jumblrPost) throws MalformedURLException {
        if(jumblrPost == null) {
            return null;
        }
        TumblrPost post = new TumblrPost();
        post.setLiked(jumblrPost.isLiked());

        if(jumblrPost.getAuthorId() != null) {
            TumblrUser user = new TumblrUser(USER);
            user.setId(jumblrPost.getAuthorId());
            post.setAuthor(user);
        }
        Date date = new Date();
        date.setTime(jumblrPost.getTimestamp() * 1000);
        post.setCreationTime(date);
        post.setId(String.valueOf(jumblrPost.getId()));
        post.setPermalink(new URL(jumblrPost.getPostUrl()));

        long noteCount = jumblrPost.getNoteCount();
        if(jumblrPost.getNotes() != null) {
            long reblogCount = jumblrPost.getNotes()
                    .stream()
                    .filter(note -> "reblog".equals(note.getType()))
                    .count();
            post.setSharedCount((int) reblogCount);
            post.setLikeCount((int) (noteCount - reblogCount));
        }
        post.setTags(jumblrPost.getTags());
        if (jumblrPost.getRebloggedFromName() != null) {
            List<TumblrUser> toList = new ArrayList<>();
            TumblrUser rebloggedFrom = new TumblrUser(BLOG);
            rebloggedFrom.setName(jumblrPost.getRebloggedFromName());
            toList.add(rebloggedFrom);
            post.setTo(toList);
        }
        post.setReblogKey(jumblrPost.getReblogKey());

        return postTypeSpecificConversion(post, jumblrPost);
    }

    /**
     * Figures out type of the post and ensures post is converted to the correct type
     * @param post Post to move attributes to
     * @param jumblrPost Post to move attributes from
     * @return the converted post
     */
    static TumblrPost postTypeSpecificConversion(TumblrPost post, Post jumblrPost) {
        switch(jumblrPost.getType().toUpperCase()) {
            case "TEXT":
                return textPostConverter(post, jumblrPost);
            case "PHOTO":
                return photoPostConverter(post, jumblrPost);
            case "QUOTE":
                return quotePostConverter(post, jumblrPost);
            case "LINK":
                return linkPostConverter(post, jumblrPost);
            case "CHAT":
                return chatPostConverter(post, jumblrPost);
            case "AUDIO":
                return audioPostConverter(post, jumblrPost);
            case "VIDEO":
                return videoPostConverter(post, jumblrPost);
            case "ANSWER":
                return answerPostConverter(post, jumblrPost);
            default:
                post.setType(UNKNOWN);
                return post;
        }
    }

    /**
     * Converts all Photo-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost photoPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        PhotoPost post = (PhotoPost)jumblrPost;
        tumblrPost.setType(IMAGE);

        String body = post.getCaption();
        String text = "";
        if(!SocialMediaUtil.isNullOrWhitespace(body)) {
            text += body;
        }
        String urls = post.getPhotos()
                .stream()
                .map(photo -> photo.getOriginalSize().getUrl())
                .reduce("", (a, b) -> a + "\n" + "\"" + b +"\"");
        if(!SocialMediaUtil.isNullOrWhitespace(urls)) {
            text += "\n" + urls;
        }
        tumblrPost.setText(text);
        return tumblrPost;
    }

    /**
     * Converts all Quote-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost quotePostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(QUOTE);
        QuotePost quotePost = (QuotePost)jumblrPost;
        String source = quotePost.getSource();
        String body = quotePost.getText();
        String text = "";
        if(!SocialMediaUtil.isNullOrWhitespace(body)) {
            text += body;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(source)) {
            text += "\n\nsource: \"" + source + "\"";
        }

        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Text-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost textPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(TEXT);
        TextPost textPost = (TextPost)jumblrPost;
        String text = "";
        String title = textPost.getTitle();
        String body = textPost.getBody();
        if(!SocialMediaUtil.isNullOrWhitespace(title)) {
            text += title + "\n\n";
        }
        if(!SocialMediaUtil.isNullOrWhitespace(body)) {
            text += body;
        }
        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Link-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost linkPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(LINK);
        LinkPost linkPost = (LinkPost)jumblrPost;
        String text = "";
        String title = linkPost.getTitle();
        String description = linkPost.getDescription();
        String url = linkPost.getLinkUrl();

        if(!SocialMediaUtil.isNullOrWhitespace(title)) {
            text += title;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(description)) {
            text += "\n\n" + description;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(url)) {
            text += "\n\n" + "source: \"" + linkPost.getLinkUrl() + "\"";
        }
        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Chat-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost chatPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(CHAT);
        ChatPost chatPost = (ChatPost)jumblrPost;
        String text = "";
        String title = chatPost.getTitle();
        String body = chatPost.getBody();
        if(!SocialMediaUtil.isNullOrWhitespace(title)) {
            text += title;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(body)) {
            text += "\n\n" + body;
        }

        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Audio-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost audioPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(AUDIO);
        AudioPost audioPost = (AudioPost)jumblrPost;
        String text = "";
        String title = audioPost.getSourceTitle();
        String caption = audioPost.getCaption();
        String source = audioPost.getSourceUrl();
        if(!SocialMediaUtil.isNullOrWhitespace(title)) {
            text += title;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(caption)) {
            text += "\n\n" + caption;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(source)) {
            text += "\n\n" + source;
        }
        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Video-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost videoPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(VIDEO);
        VideoPost videoPost = (VideoPost)jumblrPost;
        String caption = videoPost.getCaption();
        String thumbnail = videoPost.getThumbnailUrl();
        String text = "";

        if(!SocialMediaUtil.isNullOrWhitespace(caption)) {
            text += caption;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(thumbnail)) {
            text += "\n\n" + "\"" + thumbnail + "\"";
        }

        tumblrPost.setText(text);

        return tumblrPost;
    }

    /**
     * Converts all Answer-post specific attributes
     * @param tumblrPost TumblrPost to move attributes to
     * @param jumblrPost JumblrPost to move attributes from
     * @return the converted post
     */
    static TumblrPost answerPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(ANSWER);
        AnswerPost answerPost = (AnswerPost)jumblrPost;
        String askingName = answerPost.getAskingName();

        TumblrUser questionUser = null;
        if(askingName != null ) {
            questionUser = new TumblrUser(BLOG);
            questionUser.setName(askingName);
        }
        if(tumblrPost.getTo() == null && questionUser != null){
            tumblrPost.setTo(Arrays.asList(questionUser));
        } else if( questionUser != null){
            List<TumblrUser> toList = (List<TumblrUser>)tumblrPost.getTo();
            toList.add(questionUser);
            tumblrPost.setTo(toList);
        }

        String text = "";
        String question = answerPost.getQuestion();
        String answer = answerPost.getAnswer();

        if(!SocialMediaUtil.isNullOrWhitespace(askingName)) {
            text += askingName;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(question)) {
            text += "\n\n" + question;
        }
        if(!SocialMediaUtil.isNullOrWhitespace(answer)) {
            text += "\n\n" + answer;
        }
        tumblrPost.setText(text);

        return tumblrPost;
    }
}
