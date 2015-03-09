package hci.divinesymphony.net.flashtrainer.beans;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by rick on 3/8/15.
 */
public class ProblemSet {

    private final DisplayItem problem;
    private List<DisplayItem> responses;

    public ProblemSet(DisplayItem problem, List<DisplayItem> responses) {
        this.problem = problem;

        this.responses = new ArrayList<DisplayItem>(responses.size());
        this.responses.addAll(responses);
    }

    public DisplayItem getProblem() {
        return problem;
    }

    public List<DisplayItem> getResponses() {
        return this.responses;
    }
}
