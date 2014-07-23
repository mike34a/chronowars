package com.miksinouf.chronowars.domain.board;

public class MoveResult {
    public final MoveResultType moveResultType;
    public final Position position;

    public MoveResult(MoveResultType moveResultType, int x, int y) {
        this.moveResultType = moveResultType;
        this.position = new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveResult that = (MoveResult) o;

        if (moveResultType != that.moveResultType) return false;
        if (!position.equals(that.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moveResultType != null ? moveResultType.hashCode() : 0;
        result = 31 * result + (position.hashCode());
        return result;
    }
}
