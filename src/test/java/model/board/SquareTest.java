package model.board;

import static model.board.Sugar.square;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import model.enums.Column;
import model.enums.Row;
import model.enums.ViewVector;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {

    private Square e_4 = square(Column.E, Row.R4);

    @Before
    public void setUp() {
    }

    @Test
    public void square_name_format_is_as_follows() {
        Square a_1 = square(Column.A, Row.R1);
        assertThat(a_1.toString(), equalTo("A_1"));
    }

    @Test
    public void neighbor_UP() {
        assertThat(e_4.neighbor(ViewVector.UP), equalTo(square(Column.E, Row.R5)));
    }

    @Test
    public void neighbor_DOWN() {
        assertThat(e_4.neighbor(ViewVector.DOWN), equalTo(square(Column.E, Row.R3)));
    }

    @Test
    public void neighbor_LEFT() {
        assertThat(e_4.neighbor(ViewVector.LEFT), equalTo(square(Column.D, Row.R4)));
    }

    @Test
    public void neighbor_RIGHT() {
        assertThat(e_4.neighbor(ViewVector.RIGHT), equalTo(square(Column.F, Row.R4)));
    }

    @Test
    public void neighbor_LEFT_UP() {
        assertThat(e_4.neighbor(ViewVector.LEFT_UP), equalTo(square(Column.D, Row.R5)));
    }

    @Test
    public void neighbor_UP_RIGHT() {
        assertThat(e_4.neighbor(ViewVector.RIGHT_UP), equalTo(square(Column.F, Row.R5)));
    }

    @Test
    public void neighbor_LEFT_DOWN() {
        assertThat(e_4.neighbor(ViewVector.LEFT_DOWN), equalTo(square(Column.D, Row.R3)));
    }

    @Test
    public void neighbor_RIGHT_DOWN() {
        assertThat(e_4.neighbor(ViewVector.RIGHT_DOWN), equalTo(square(Column.F, Row.R3)));
    }

    @Test
    public void neighbor_at_board_edge_is_null() {
        Square a_1 = square(Column.A, Row.R1);

        assertNull(a_1.neighbor(ViewVector.LEFT));
        assertNull(a_1.neighbor(ViewVector.DOWN));
        assertNull(a_1.neighbor(ViewVector.LEFT_UP));
        assertNull(a_1.neighbor(ViewVector.LEFT_DOWN));
        assertNull(a_1.neighbor(ViewVector.RIGHT_DOWN));

        assertNotNull(a_1.neighbor(ViewVector.UP));
        assertNotNull(a_1.neighbor(ViewVector.RIGHT));
        assertNotNull(a_1.neighbor(ViewVector.RIGHT_UP));
    }

}
