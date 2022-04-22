package me.altmanager;


public final class Alt {
    private String mask = "";
    public String uuid;
    private final String username;
    private String password;
    private boolean mineplex, hypixel, cubecraft, hive;
    

    public Alt(String username, String password) {
        this(username, password, "");
    }

    public Alt(String username, String password, String mask) {
        this.username = username;
        this.password = password;
        this.mask = mask;
    }
    
    public Alt(String username, String password, String mask, String uuid) {
        this.username = username;
        this.password = password;
        this.mask = mask;
        this.uuid = uuid;
    }
    
    public Alt(String mask, String username, String password, boolean mineplex, boolean hypixel, boolean cubecraft,
			boolean hive) {
		super();
		this.mask = mask;
		this.username = username;
		this.password = password;
		this.mineplex = mineplex;
		this.hypixel = hypixel;
		this.cubecraft = cubecraft;
		this.hive = hive;
	}

	public boolean isMineplex() {
		return mineplex;
	}

	public void setMineplex(boolean mineplex) {
		this.mineplex = mineplex;
	}

	public boolean isHypixel() {
		return hypixel;
	}

	public void setHypixel(boolean hypixel) {
		this.hypixel = hypixel;
	}

	public boolean isCubecraft() {
		return cubecraft;
	}

	public void setCubecraft(boolean cubecraft) {
		this.cubecraft = cubecraft;
	}

	public boolean isHive() {
		return hive;
	}

	public void setHive(boolean hive) {
		this.hive = hive;
	}

	public String getMask() {
        return this.mask;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
}

