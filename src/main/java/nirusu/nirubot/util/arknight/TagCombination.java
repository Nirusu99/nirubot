package nirusu.nirubot.util.arknight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagCombination implements Comparable<TagCombination> {
    private Set<Operator> possibleOperator;
    private List<String> tags;

    public TagCombination(final List<String> tags) {
        this.tags = new ArrayList<>();
        tags.forEach(this.tags::add);
        this.possibleOperator = new HashSet<>();
    }

    public void addOperator(final Operator op) {
        possibleOperator.add(op);
    }

    @Override
    public int compareTo(TagCombination o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (String str : tags) {
            hash += str.hashCode();
        }
        return hash;
    }

	public boolean accepts(Operator o) {
        for (String tag : tags) {
            if (!o.hasTag(tag)) {
                return false;
            }
        }
        if (o.getRarity() == 6 && !tags.contains("TOP_OPERATOR")) {
            return false;
        }

		return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        TagCombination cb = (TagCombination) o;
        return this.hashCode() - cb.hashCode() == 0;
    }

	public boolean hasOperator() {
		return !possibleOperator.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Tags: **");
        tags.forEach(str -> out.append(str).append(", "));
        out.replace(out.length() - 2, out.length() - 1, "");
        out.append("** Operators: **");
        possibleOperator.forEach(op -> out.append(op).append(", "));
        out.replace(out.length() - 2, out.length() - 1, "");
        out.append("**");
        return out.toString() + " Lowest Rarity: "  + getLowestRarity();
    }

    public int getLowestRarity() {
        int lowest = 7;
        for (Operator o : possibleOperator) {
            if (o.getRarity() < lowest) {
                lowest = o.getRarity();
            }
        }
        if (lowest == 7) {
            return -1;
        }
        return lowest;
    }
    
}
