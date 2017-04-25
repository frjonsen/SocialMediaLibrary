package facebook;

import facebook.mocks.FacebookLibraryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import socialmedia.SocialMediaUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class FacebookCommentTest {

    private FacebookComment comment = null;

    @BeforeEach
    void init() {
        this.comment = new FacebookComment() {};
    }

    @Test
    @DisplayName("should set and get message unchanged")
    void testMessage() {
        String msg = "somemessage";
        comment.setMessage(msg);
        assertEquals(msg, comment.getMessage());
    }

    @Test
    @DisplayName("should set and get id unchanged")
    void testId() {
        String commentId = "someid";
        comment.setId(commentId);
        assertEquals(commentId, comment.getId());
    }

    @Test
    @DisplayName("should set and get from-user unchanged")
    void testFrom() {
        String userid = "someid";
        FacebookUser user = new FacebookUser();
        user.setId(userid);
        comment.setFrom(user);
        assertEquals(userid, comment.getFrom().getId());
    }

    @Test
    @DisplayName("should set and get direct assignment of creation date unchanged")
    void testDirectCreationDate() {
        ZonedDateTime now = ZonedDateTime.now();
        comment.setCreated(now);
        assertEquals(now, comment.getCreated());
    }

    @Test
    @DisplayName("should set, convert and get creation time")
    void testCreationDateConversion() throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        comment.setCreated(d);
        assertEquals(2017, comment.getCreated().getYear());
        assertEquals(16, comment.getCreated().getHour());
        assertEquals(ZoneOffset.UTC, comment.getCreated().getZone());
    }

    @Test
    @DisplayName("should set and get like count unchanged")
    void testLikeCount() {
        int count = 7;
        comment.setLikeCount(count);
        assertEquals(count, comment.getLikeCount());
    }
}