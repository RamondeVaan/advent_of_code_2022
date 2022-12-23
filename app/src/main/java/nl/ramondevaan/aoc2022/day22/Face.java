package nl.ramondevaan.aoc2022.day22;

public enum Face {

  TOP {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> TOP_TO_LEFT;
        case RIGHT -> TOP_TO_RIGHT;
        case DOWN -> TOP_TO_FRONT;
        case UP -> TOP_TO_BACK;
      };
    }
  },
  BOTTOM {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> BOTTOM_TO_RIGHT;
        case RIGHT -> BOTTOM_TO_LEFT;
        case DOWN -> BOTTOM_TO_FRONT;
        case UP -> BOTTOM_TO_BACK;
      };
    }
  },
  LEFT {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> LEFT_TO_BACK;
        case RIGHT -> LEFT_TO_FRONT;
        case DOWN -> LEFT_TO_BOTTOM;
        case UP -> LEFT_TO_TOP;
      };
    }
  },
  RIGHT {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> RIGHT_TO_FRONT;
        case RIGHT -> RIGHT_TO_BACK;
        case DOWN -> RIGHT_TO_BOTTOM;
        case UP -> RIGHT_TO_TOP;
      };
    }
  },
  FRONT {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> FRONT_TO_LEFT;
        case RIGHT -> FRONT_TO_RIGHT;
        case DOWN -> FRONT_TO_BOTTOM;
        case UP -> FRONT_TO_TOP;
      };
    }
  },
  BACK {
    @Override
    public FaceDirection neighbor(final Direction direction) {
      return switch (direction) {
        case LEFT -> BACK_TO_RIGHT;
        case RIGHT -> BACK_TO_LEFT;
        case DOWN -> BACK_TO_BOTTOM;
        case UP -> BACK_TO_TOP;
      };
    }
  };

  public final static FaceDirection TOP_TO_LEFT = new FaceDirection(LEFT, Direction.DOWN);
  public final static FaceDirection TOP_TO_RIGHT = new FaceDirection(RIGHT, Direction.DOWN);
  public final static FaceDirection TOP_TO_FRONT = new FaceDirection(FRONT, Direction.DOWN);
  public final static FaceDirection TOP_TO_BACK = new FaceDirection(BACK, Direction.DOWN);


  public final static FaceDirection BOTTOM_TO_LEFT = new FaceDirection(LEFT, Direction.UP);
  public final static FaceDirection BOTTOM_TO_RIGHT = new FaceDirection(RIGHT, Direction.UP);
  public final static FaceDirection BOTTOM_TO_FRONT = new FaceDirection(FRONT, Direction.UP);
  public final static FaceDirection BOTTOM_TO_BACK = new FaceDirection(BACK, Direction.UP);


  public final static FaceDirection RIGHT_TO_BOTTOM = new FaceDirection(BOTTOM, Direction.RIGHT);
  public final static FaceDirection RIGHT_TO_TOP = new FaceDirection(TOP, Direction.LEFT);
  public final static FaceDirection RIGHT_TO_FRONT = new FaceDirection(FRONT, Direction.LEFT);
  public final static FaceDirection RIGHT_TO_BACK = new FaceDirection(BACK, Direction.RIGHT);


  public final static FaceDirection LEFT_TO_BOTTOM = new FaceDirection(BOTTOM, Direction.LEFT);
  public final static FaceDirection LEFT_TO_TOP = new FaceDirection(TOP, Direction.RIGHT);
  public final static FaceDirection LEFT_TO_FRONT = new FaceDirection(FRONT, Direction.RIGHT);
  public final static FaceDirection LEFT_TO_BACK = new FaceDirection(BACK, Direction.LEFT);


  public final static FaceDirection FRONT_TO_BOTTOM = new FaceDirection(BOTTOM, Direction.UP);
  public final static FaceDirection FRONT_TO_TOP = new FaceDirection(TOP, Direction.UP);
  public final static FaceDirection FRONT_TO_LEFT = new FaceDirection(LEFT, Direction.LEFT);
  public final static FaceDirection FRONT_TO_RIGHT = new FaceDirection(RIGHT, Direction.RIGHT);


  public final static FaceDirection BACK_TO_BOTTOM = new FaceDirection(BOTTOM, Direction.DOWN);
  public final static FaceDirection BACK_TO_TOP = new FaceDirection(TOP, Direction.DOWN);
  public final static FaceDirection BACK_TO_LEFT = new FaceDirection(LEFT, Direction.RIGHT);
  public final static FaceDirection BACK_TO_RIGHT = new FaceDirection(RIGHT, Direction.LEFT);


  public abstract FaceDirection neighbor(final Direction direction);
}
