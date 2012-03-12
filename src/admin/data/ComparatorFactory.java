package admin.data;

import java.util.Comparator;

import data.Contestant;

public class ComparatorFactory {

	public static final int CONTNT_FIRST_NAME = 0;
	public static final int CONTNT_LAST_NAME = 1;
	public static final int CONTNT_ID = 2;
	public static final int CONTNT_TRIBE = 3;
	public static final int CONTNT_DATE = 4;

	public static Comparator<Contestant> getComparator(int type) {
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
}
