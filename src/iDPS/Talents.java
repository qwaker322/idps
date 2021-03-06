package iDPS;

import iDPS.model.Calculations;
import iDPS.model.Calculations.ModelType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

public class Talents implements Cloneable {
	
	public enum Tree { Assassination, Combat, Subtetly }
	
	private ModelType model;
	private static HashMap<String,Talent> talents;
	private static HashMap<Integer,Talent> talentPosition;
	private HashMap<Talent,Integer> distribution;
	
	public Talents() {
		model = Calculations.ModelType.Combat;
		distribution = new HashMap<Talent,Integer>();
		for (Talent t: talents.values())
			distribution.put(t, 0);
	}
	
	@SuppressWarnings("unchecked")
	public Talents(Element root) {
		this();
		for (Element elem: (List<Element>) root.getChildren()) {
			if (elem.getName().equals("ModelType")) {
				model = ModelType.valueOf(elem.getText());
			} else if (talents.containsKey(elem.getName())) {
				Talent t = talents.get(elem.getName());
				distribution.put(t, Integer.parseInt(elem.getText()));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Element toXML() {
		Element elem = new Element("talents");
		Element sub = new Element("ModelType");
		sub.setText(model.name());
		elem.getChildren().add(sub);
		for (Talent t: getTalents()) {
			int points = getTalentPoints(t);
			if (points==0)
				continue;
			sub = new Element(t.getIdentifier());
			sub.setText(String.valueOf(points));
			elem.getChildren().add(sub);
		}
		return elem;
	}
	
	public int getTalentPoints(String identifier) {
		Talent t = talents.get(identifier);
		if (t == null)
			System.err.println("No such Talent: "+identifier);
		return getTalentPoints(t);
	}
	
	public int getTalentPoints(Talent t) {
		if (distribution.containsKey(t))
			return distribution.get(t);
		return 0;
	}
	
	public void setTalentPoints(String identifier, int points) {
		Talent t = talents.get(identifier);
		distribution.put(t, points);
	}
	
	public void setTalentPoints(Talent t, int points) {
		distribution.put(t, points);
	}
	
	public static Collection<Talent> getTalents(Tree tree) {
		ArrayList<Talent> matches = new ArrayList<Talent>();
		for (Talent t: talents.values()) {
			if (t.tree == tree)
				matches.add(t);
		}
		Collections.sort(matches);
		return matches;
	}
	
	public static Collection<Talent> getTalents() {
		return talents.values();
	}
	
	public static void load() {
		talents = new HashMap<String,Talent>();
		talentPosition = new HashMap<Integer,Talent>();
		
		createTalent("IEvisc", Tree.Assassination, 1, 0, "Imp. Eviscerate", 3);
		createTalent("Malice", Tree.Assassination, 1, 2, "Malice", 5);
		createTalent("Ruth", Tree.Assassination, 2, 3, "Ruthlessness", 3);
		createTalent("BSpatter", Tree.Assassination, 2, 4, "Blood Spatter", 2);
		createTalent("PWounds", Tree.Assassination, 2, 5, "Puncturing Wounds", 3);
		createTalent("IEA", Tree.Assassination, 3, 7, "Imp. Expose Armor", 2);
		createTalent("Lethality", Tree.Assassination, 3, 8, "Lethality", 5);
		createTalent("VPoisons", Tree.Assassination, 4, 9, "Vile Poisons", 3);
		createTalent("IPoisons", Tree.Assassination, 4, 10, "Imp. Poisons", 5);
		createTalent("SFate", Tree.Assassination, 6, 15, "Seal Fate", 5);
		createTalent("Murder", Tree.Assassination, 6, 16, "Murder", 2);
		createTalent("OKill", Tree.Assassination, 7, 18, "Overkill", 1);
		createTalent("FAttacks", Tree.Assassination, 8, 20, "Focused Attacks", 3);
		createTalent("FWeakness", Tree.Assassination, 8, 21, "Find Weakness", 3);
		//createTalent("MPoisoner", Tree.Assassination, 9, "Master Poisoner", 3);
		//createTalent("Mutilate", Tree.Assassination, 9, "Mutilate", 1);
		//createTalent("TtT", Tree.Assassination, 9, "Turn the Tables", 3);
		//createTalent("CttC", Tree.Assassination, 10, "Cut to the Chase", 5);
		createTalent("HfB", Tree.Assassination, 11, 26, "Hunger For Blood", 1);
		
		createTalent("ISS", Tree.Combat, 1, 28, "Imp. Sinister Strike", 2);
		createTalent("DWield", Tree.Combat, 1, 29, "Dual Wield Spec.", 5);
		createTalent("ISnD", Tree.Combat, 2, 30, "Imp. Slice and Dice", 2);
		createTalent("Precision", Tree.Combat, 2, 32, "Precision", 5);
		createTalent("CQC", Tree.Combat, 3, 35, "Close Quarters Combat", 5);
		createTalent("LR", Tree.Combat, 4, 38, "Lighning Reflexes", 3);
		createTalent("Aggr", Tree.Combat, 4, 39, "Aggression", 5);
		createTalent("Mace", Tree.Combat, 5, 40, "Mace Spec.", 5);
		createTalent("BF", Tree.Combat, 5, 41, "Blade Flurry", 1);
		createTalent("HnS", Tree.Combat, 5, 42, "Hack and Slash", 5);
		createTalent("WExp", Tree.Combat, 6, 43, "Weapon Expertise", 2);
		createTalent("BTwist", Tree.Combat, 6, 44, "Blade Twisting", 2);
		createTalent("Vitality", Tree.Combat, 7, 45, "Vitality", 3);
		createTalent("AR", Tree.Combat, 7, 46, "Adrenaline Rush", 1);
		createTalent("CPotency", Tree.Combat, 8, 49, "Combat Potency", 5);
		createTalent("SAttacks", Tree.Combat, 9, 51, "Surprise Attacks", 1);
		createTalent("SCombat", Tree.Combat, 9, 52, "Savage Combat", 2);
		createTalent("PotW", Tree.Combat, 10, 53, "Prey on the Weak", 5);
		createTalent("KS", Tree.Combat, 11, 54, "Killing Spree", 1);
		
		createTalent("RStrikes", Tree.Subtetly, 1, 55, "Relentless Strikes", 5);
		createTalent("Opp", Tree.Subtetly, 1, 57, "Opportunity", 2);
		createTalent("SBlades", Tree.Subtetly, 3, 63 ,"Serrated Blades", 3);
		createTalent("Init", Tree.Subtetly, 4, 65, "Initiative", 3);
		createTalent("IAmb", Tree.Subtetly, 4, 66, "Imp. Ambush", 2);
		createTalent("Prep", Tree.Subtetly, 5, 68, "Preparation", 1);
		createTalent("DDeeds", Tree.Subtetly, 5, 69, "Dirty Deeds", 5);
		createTalent("Deadly", Tree.Subtetly, 6, 72, "Deadlyness", 5);
		createTalent("SCalling", Tree.Subtetly, 8, 76, "Sinister Calling", 5);
		createTalent("HaT", Tree.Subtetly, 9, 78, "Honor Among Thieves", 3);
		createTalent("SStep", Tree.Subtetly, 9, 79, "Shadow Step", 1);
		createTalent("FTricks", Tree.Subtetly, 9, 80, "Filthy Tricks", 3);
		createTalent("SftS", Tree.Subtetly, 10, 81, "Slaughter ft. Shadows", 5);
		createTalent("SDance", Tree.Subtetly, 11, 82, "Shadow Dance", 1);
	}
	
	private static void createTalent(String identifier, Tree tree, int rank, int position, String name, int maxPoints) {
		Talent t = new Talent(identifier, tree, rank, position, name, maxPoints);
		talents.put(identifier, t);
		talentPosition.put(position, t);
	}
	
	@SuppressWarnings("unchecked")
	public Talents clone() {
		Talents clone = new Talents();
		clone.distribution = (HashMap<Talent, Integer>) this.distribution.clone();
		clone.model = this.model;
		return clone;
	}
	
	public static class Talent implements Comparable<Talent> {

		private String identifier;
		private Tree tree;
		private int rank;
		private String name;
		private int maxPoints;
		private int position;
		
		public Talent(String identifier, Tree tree, int rank, int position, String name, int maxPoints) {
			this.identifier = identifier;
			this.tree = tree;
			this.rank = rank;
			this.name = name;
			this.position = position;
			this.maxPoints = maxPoints;
		}
		
		public String getIdentifier() {
			return identifier;
		}

		public int getPosition() {
			return position;
		}
		
		public Tree getTree() {
			return tree;
		}

		public int getRank() {
			return rank;
		}

		public String getName() {
			return name;
		}

		public int getMaxPoints() {
			return maxPoints;
		}
		
		public int compareTo(Talent t) {
			if (rank > t.rank)
				return 1;
			else if (rank < t.rank)
				return -1;
			return 0;
		}
		
	}

	public Calculations.ModelType getModel() {
		return model;
	}

	public void setModel(Calculations.ModelType model) {
		this.model = model;
	}
	public static HashMap<Integer, Talent> getTalentPosition() {
		return talentPosition;
	}
}
