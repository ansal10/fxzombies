package solver;

import game.Coordinate;
import game.Maze;

import java.util.List;

public interface Zombie {
	public List<Coordinate> solve(Maze m);
}