package admin.data;

import java.util.Comparator;

import data.Contestant;
import data.User;

public class ComparatorFactory {

	// TODO: enum
	public static final int CONTNT_FIRST_NAME = 0;
	public static final int CONTNT_LAST_NAME = 1;
	public static final int CONTNT_ID = 2;
	public static final int CONTNT_TRIBE = 3;
	public static final int CONTNT_DATE = 4;
	
	public static final int USER_FIRST_NAME = 10;
	public static final int USER_LAST_NAME = 11;
	public static final int USER_ID = 12;
	public static final int USER_POINTS = 13;
	public static final int USER_ULT_PICK = 14;
	public static final int USER_WEEKLY_PICK = 15;

	public static Comparator<Contestant> getContComparator(int type) {
		switch (type) {
		case CONTNT_FIRST_NAME:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					String f1 = c1.getFirstName().toLowerCase();
					String f2 = c2.getFirstName().toLowerCase();
					return (f1.compareTo(f2));
				}
			};
			
		case CONTNT_LAST_NAME:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					String l1 = c1.getLastName().toLowerCase();
					String l2 = c2.getLastName().toLowerCase();
					return (l1.compareTo(l2));
				}
			};
			
		case CONTNT_ID:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getID().compareTo(c2.getID()));
				}
			};
			
		case CONTNT_DATE:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getCastDate() - c2.getCastDate());
				}
			};
			
		case CONTNT_TRIBE:
			return new Comparator<Contestant>() {
				@Override
				public int compare(Contestant c1, Contestant c2) {
					return (c1.getTribe().compareTo(c2.getTribe()));
				}
			};
		
		default:
			return null;
		}
	}
	
	public static Comparator<User> getUserComparator(int type) {
		switch (type) {
		case USER_FIRST_NAME:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					String f1 = u1.getFirstName().toLowerCase();
					String f2 = u2.getFirstName().toLowerCase();
					return (f1.compareTo(f2));
				}
			};
			
		case USER_LAST_NAME:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					String l1 = u1.getLastName().toLowerCase();
					String l2 = u2.getLastName().toLowerCase();
					return (l1.compareTo(l2));
				}
			};
			
		case USER_ID:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					return (u1.getID().compareTo(u2.getID()));
				}
			};
			
		case USER_POINTS:
			return new Comparator<User>() {
				@Override
				public int compare(User u1, User u2) {
					return (u1.getPoints() - u2.getPoints());
				}
			};
			
		case USER_ULT_PICK:
			return new Comparator<User>() {
				Comparator<Contestant> comp = 
						ComparatorFactory.getContComparator(CONTNT_ID);
				
				@Override
				public int compare(User u1, User u2) {
					return comp.compare(u1.getUltimatePick(), u2.getUltimatePick());
				}
			};
			
		case USER_WEEKLY_PICK:
			return new Comparator<User>() {
				Comparator<Contestant> comp = 
						ComparatorFactory.getContComparator(CONTNT_ID);
				
				@Override
				public int compare(User u1, User u2) {
					return comp.compare(u1.getWeeklyPick(), u2.getWeeklyPick());
				}
			};
		
		default:
			return null;
		}
	}
}
