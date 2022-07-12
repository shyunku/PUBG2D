package swe1.twodpubg.variables.item;

public class ItemInfo {
	private String itemName;
	private int itemType;
	private String itemPicName;

	public static final int HAND = 0; // 아이템 드랍 x
	public static final int S12K = 1;
	public static final int Kar98k = 2;
	public static final int M416 = 3;
	public static final int P92 = 4;
	public static final int AMMO = 5;
	public static final int ARMOR = 6;
	public static final int SCOPE = 7;
	public static final int GRENADE = 8;
	public static final int SMOKEGRENADE = 9;
	public static final int SUPPLEMENT = 10;

	public static GunInfo INFO_S12K;
	public static GunInfo INFO_Kar98k;
	public static GunInfo INFO_P92;
	public static GunInfo INFO_M416;
	public static GunInfo INFO_HAND;
	public static ItemInfo INFO_AMMO;
	public static ItemInfo INFO_ARMOR;
	public static ItemInfo INFO_SCOPE;
	public static ItemInfo INFO_GRENADE;
	public static ItemInfo INFO_SMOKEGRENADE;
	public static ItemInfo INFO_SUPPLEMENT;

	public ItemInfo() {
	}

	public ItemInfo(int type) {
		setItemType(type);
		switch (type) {
		case AMMO:
			setItemName("총알");
			setItemPicName("ammo");
			break;
		case ARMOR:
			setItemName("방어구");
			setItemPicName("armor");
			break;
		case SCOPE:
			setItemName("스코프");
			setItemPicName("scope");
			break;
		case GRENADE:
			setItemName("수류탄");
			setItemPicName("grenade");
			break;
		case SMOKEGRENADE:
			setItemName("연막탄");
			setItemPicName("smokebomb");
			break;
		case SUPPLEMENT:
			setItemName("치료용품");
			setItemPicName("HealKit");
			break;
		}
	}

	public static ItemInfo forInfo(int type) {
		switch (type) {
		case AMMO:
			if (INFO_AMMO == null)
				INFO_AMMO = new ItemInfo(AMMO);
			return INFO_AMMO;
		case ARMOR:
			if (INFO_ARMOR == null)
				INFO_ARMOR = new ItemInfo(ARMOR);
			return INFO_ARMOR;
		case SCOPE:
			if (INFO_SCOPE == null)
				INFO_SCOPE = new ItemInfo(SCOPE);
			return INFO_SCOPE;
		case GRENADE:
			if (INFO_GRENADE == null)
				INFO_GRENADE = new ItemInfo(GRENADE);
			return INFO_GRENADE;
		case SMOKEGRENADE:
			if (INFO_SMOKEGRENADE == null)
				INFO_SMOKEGRENADE = new ItemInfo(SMOKEGRENADE);
			return INFO_SMOKEGRENADE;
		case S12K:
			if (INFO_S12K == null) {
				INFO_S12K = new GunInfo(S12K);
				INFO_S12K.setItemType(S12K);
			}
			return INFO_S12K;
		case Kar98k:
			if (INFO_Kar98k == null) {
				INFO_Kar98k = new GunInfo(Kar98k);
				INFO_Kar98k.setItemType(Kar98k);	
			}
			return INFO_Kar98k;
		case P92:
			if (INFO_P92 == null) {
				INFO_P92 = new GunInfo(P92);
				INFO_P92.setItemType(P92);
			}
			return INFO_P92;
		case M416:
			if (INFO_M416 == null) {
				INFO_M416 = new GunInfo(M416);
				INFO_M416.setItemType(M416);
			}
			return INFO_M416;
		case HAND:
			if (INFO_HAND == null)
				INFO_HAND = new GunInfo(HAND);
			return INFO_HAND;
		case SUPPLEMENT:
			if (INFO_SUPPLEMENT == null)
				INFO_SUPPLEMENT = new ItemInfo(SUPPLEMENT);
			return INFO_SUPPLEMENT;
		}
		return null;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getItemPicName() {
		return itemPicName;
	}

	public void setItemPicName(String itemPicName) {
		this.itemPicName = itemPicName;
	}
}
