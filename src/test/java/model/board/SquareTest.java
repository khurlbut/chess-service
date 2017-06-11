package model.board;

import static model.board.Sugar.square;
import static model.enums.Column.A;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Column.F;
import static model.enums.Row.R1;
import static model.enums.Row.R3;
import static model.enums.Row.R4;
import static model.enums.Row.R5;
import static model.enums.ViewVector.DOWN;
import static model.enums.ViewVector.LEFT;
import static model.enums.ViewVector.LEFT_DOWN;
import static model.enums.ViewVector.LEFT_UP;
import static model.enums.ViewVector.RIGHT;
import static model.enums.ViewVector.RIGHT_DOWN;
import static model.enums.ViewVector.RIGHT_UP;
import static model.enums.ViewVector.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {

    private Square e_4 = square(E, R4);

    @Before
    public void setUp() {
    }

    @Test
    public void square_name_format_is_as_follows() {
        Square a_1 = square(A, R1);
        assertThat(a_1.toString(), equalTo("A_1"));
    }

    @Test
    public void neighbor_UP() {
        assertThat(e_4.neighbor(UP), equalTo(square(E, R5)));
    }

    @Test
    public void neighbor_DOWN() {
        assertThat(e_4.neighbor(DOWN), equalTo(square(E, R3)));
    }

    @Test
    public void neighbor_LEFT() {
        assertThat(e_4.neighbor(LEFT), equalTo(square(D, R4)));
    }

    @Test
    public void neighbor_RIGHT() {
        assertThat(e_4.neighbor(RIGHT), equalTo(square(F, R4)));
    }

    @Test
    public void neighbor_LEFT_UP() {
        assertThat(e_4.neighbor(LEFT_UP), equalTo(square(D, R5)));
    }

    @Test
    public void neighbor_UP_RIGHT() {
        assertThat(e_4.neighbor(RIGHT_UP), equalTo(square(F, R5)));
    }

    @Test
    public void neighbor_LEFT_DOWN() {
        assertThat(e_4.neighbor(LEFT_DOWN), equalTo(square(D, R3)));
    }

    @Test
    public void neighbor_RIGHT_DOWN() {
        assertThat(e_4.neighbor(RIGHT_DOWN), equalTo(square(F, R3)));
    }

    @Test
    public void neighbor_at_board_edge_is_null() {
        Square a_1 = square(A, R1);

        assertNull(a_1.neighbor(LEFT));
        assertNull(a_1.neighbor(DOWN));
        assertNull(a_1.neighbor(LEFT_UP));
        assertNull(a_1.neighbor(LEFT_DOWN));
        assertNull(a_1.neighbor(RIGHT_DOWN));

        assertNotNull(a_1.neighbor(UP));
        assertNotNull(a_1.neighbor(RIGHT));
        assertNotNull(a_1.neighbor(RIGHT_UP));
    }

}
