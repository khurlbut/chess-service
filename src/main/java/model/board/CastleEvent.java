package model.board;

import static model.enums.GameEventType.CASTLE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;

public class CastleEvent extends MoveEvent {

	private final Square kingSource;
	private final Square kingTargettarget;
	private final Square rookSource;
	private final Square rookTarget;

	CastleEvent(Square kingSource, Square kingTarget, Square rookSource, Square rookTarget) {
		super(kingSource, kingTarget);

		if (rookSource == null || rookTarget == null) {
			throw new ConstructorArgsException(
					"Constructor does not allow null(s)!");
		}
		
		this.kingSource = kingSource;
		this.kingTargettarget = kingTarget;
		this.rookSource = rookSource;
		this.rookTarget = rookTarget;
	}

	public Square getKingSource() {
		return kingSource;
	}

	public Square getKingTarget() {
		return kingTargettarget;
	}

	public Square rookSource() {
		return rookSource;
	}
	
	public Square rookTarget() {
		return rookTarget;
	}

	@Override
	public String toString() {
		return CASTLE.toString() + " " + kingSource + " --> " + kingTargettarget;
	}

	@Override
	public GameEventType type() {
		return CASTLE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kingSource == null) ? 0 : kingSource.hashCode());
		result = prime * result + ((kingTargettarget == null) ? 0 : kingTargettarget.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CastleEvent other = (CastleEvent) obj;
		if (kingSource == null) {
			if (other.kingSource != null)
				return false;
		} else if (!kingSource.equals(other.kingSource))
			return false;
		if (kingTargettarget == null) {
			if (other.kingTargettarget != null)
				return false;
		} else if (!kingTargettarget.equals(other.kingTargettarget))
			return false;
		return true;
	}

}
