package model.enums;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ViewVectorTest {

    @Test
    public void direction_name_format_is_as_follows() {
        ViewVector left_up = ViewVector.LEFT_UP;
        assertThat(left_up.toString(), equalTo("left_up"));
    }

    @Test
    public void testUp() {
        ViewVector up = ViewVector.UP;
        assertThat(up.horizontalDelta(), equalTo(0));
        assertThat(up.verticalDelta(), equalTo(1));
    }

    @Test
    public void testRightUp() {
        ViewVector right_up = ViewVector.RIGHT_UP;
        assertThat(right_up.horizontalDelta(), equalTo(1));
        assertThat(right_up.verticalDelta(), equalTo(1));
    }

    @Test
    public void testRight() {
        ViewVector right = ViewVector.RIGHT;
        assertThat(right.horizontalDelta(), equalTo(1));
        assertThat(right.verticalDelta(), equalTo(0));
    }

    @Test
    public void testRightDown() {
        ViewVector right_down = ViewVector.RIGHT_DOWN;
        assertThat(right_down.horizontalDelta(), equalTo(1));
        assertThat(right_down.verticalDelta(), equalTo(-1));
    }

    @Test
    public void testDown() {
        ViewVector down = ViewVector.DOWN;
        assertThat(down.horizontalDelta(), equalTo(0));
        assertThat(down.verticalDelta(), equalTo(-1));
    }

    @Test
    public void testLeftDown() {
        ViewVector left_down = ViewVector.LEFT_DOWN;
        assertThat(left_down.horizontalDelta(), equalTo(-1));
        assertThat(left_down.verticalDelta(), equalTo(-1));
    }

    @Test
    public void testLeft() {
        ViewVector left = ViewVector.LEFT;
        assertThat(left.horizontalDelta(), equalTo(-1));
        assertThat(left.verticalDelta(), equalTo(0));
    }

    @Test
    public void testLeftUp() {
        ViewVector left_up = ViewVector.LEFT_UP;
        assertThat(left_up.horizontalDelta(), equalTo(-1));
        assertThat(left_up.verticalDelta(), equalTo(1));
    }

    @Test
    public void testRightUpUp() {
        ViewVector knightMove = ViewVector.RIGHT_UP_UP;
        assertThat(knightMove.horizontalDelta(), equalTo(1));
        assertThat(knightMove.verticalDelta(), equalTo(2));
    }

    @Test
    public void testRightRightUp() {
        ViewVector knightMove = ViewVector.RIGHT_RIGHT_UP;
        assertThat(knightMove.horizontalDelta(), equalTo(2));
        assertThat(knightMove.verticalDelta(), equalTo(1));
    }

    @Test
    public void testRightRightDown() {
        ViewVector knightMove = ViewVector.RIGHT_RIGHT_DOWN;
        assertThat(knightMove.horizontalDelta(), equalTo(2));
        assertThat(knightMove.verticalDelta(), equalTo(-1));
    }

    @Test
    public void testRightDownDown() {
        ViewVector knightMove = ViewVector.RIGHT_DOWN_DOWN;
        assertThat(knightMove.horizontalDelta(), equalTo(1));
        assertThat(knightMove.verticalDelta(), equalTo(-2));
    }

    @Test
    public void testLeftDownDown() {
        ViewVector knightMove = ViewVector.LEFT_DOWN_DOWN;
        assertThat(knightMove.horizontalDelta(), equalTo(-1));
        assertThat(knightMove.verticalDelta(), equalTo(-2));
    }

    @Test
    public void testLeftLeftDown() {
        ViewVector knightMove = ViewVector.LEFT_LEFT_DOWN;
        assertThat(knightMove.horizontalDelta(), equalTo(-2));
        assertThat(knightMove.verticalDelta(), equalTo(-1));
    }

    @Test
    public void testLeftLeftUp() {
        ViewVector knightMove = ViewVector.LEFT_LEFT_UP;
        assertThat(knightMove.horizontalDelta(), equalTo(-2));
        assertThat(knightMove.verticalDelta(), equalTo(1));
    }

}
