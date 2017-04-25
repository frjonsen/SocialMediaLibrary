package facebook;

import facebook4j.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import socialmedia.Post;
import socialmedia.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regular")
public class FacebookUtilTest {

    @Test
    @DisplayName("should convert between string-types to proper enum-types")
    void testConvertPostType() {
        String[] types = {"photo", "link", "status", "video", "offer", "nonexistent", null};
        Post.Type[] corresponding = {Post.Type.IMAGE, Post.Type.LINK, Post.Type.TEXT, Post.Type.VIDEO, Post.Type.OFFER, Post.Type.UNKNOWN, Post.Type.UNKNOWN};
        assertEquals(types.length, Post.Type.values().length + 1); // Plus 1, since we're checking null
        for (int i = 0; i < types.length; i++) {
            assertEquals(corresponding[i], FacebookUtil.convertFacebookType(types[i]));
        }
    }

    @Test
    @DisplayName("convertNameId should handle null appropriately")
    void testConvertNameIdWhenNull() {
        assertEquals(0, FacebookUtil.convertNameIdToSimpleUsers(null).size());
    }

    @Test
    @DisplayName("should return an empty list when text is null")
    void testNullHastagText() {
        assertEquals(0, FacebookUtil.getHashTags(null).size());
    }

    @Test
    @DisplayName("should return two hashtagged words")
    void testFindExistingHashTags() {
        List<String> tags = new ArrayList<>();
        tags.add("message");
        tags.add("hashtags");
        String msg = "A #message with #hashtags";
        List<String> deducedTags = FacebookUtil.getHashTags(msg);
        assertTrue(tags.containsAll(deducedTags));
        assertEquals(tags.size(), deducedTags.size());
    }

    @Test
    @DisplayName("should return zero hashtags")
    void testFindEmptyHashTags() {
        String msg = "A message with weird hashtag # hello";
        List<String> tags = FacebookUtil.getHashTags(msg);
        assertEquals(0, tags.size());
    }

    @Test
    @DisplayName("should convert a basic facebook4j category to a user")
    void testCategoryConverter() {
        Category c = Mockito.mock(Category.class);
        Mockito.when(c.getId()).thenReturn("someid");
        Mockito.when(c.getName()).thenReturn("somename");

        User user = FacebookUtil.categoryToUser(c);
        assertEquals("someid", user.getId());
        assertEquals("somename", user.getName());
    }
}
