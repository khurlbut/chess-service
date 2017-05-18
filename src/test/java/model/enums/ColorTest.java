package model.enums;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ColorTest {

    @Test
    public void color_name_format_is_as_follows() {
        Color white = Color.WHITE;
        assertThat(white.toString(), equalTo("w"));
    }

    @Test
    public void it_knows_the_opponent_color() {
        Color white = Color.WHITE;
        Color black = Color.BLACK;
        assertThat(white.opponentColor(), equalTo(Color.BLACK));
        assertThat(black.opponentColor(), equalTo(Color.WHITE));
    }

}
