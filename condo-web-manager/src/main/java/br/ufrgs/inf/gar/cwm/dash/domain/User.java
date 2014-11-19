package br.ufrgs.inf.gar.cwm.dash.domain;

public final class User {
    private String role;
    private String name;
    private String apartment;
    private boolean male;
    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(final boolean male) {
        this.male = male;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
}
