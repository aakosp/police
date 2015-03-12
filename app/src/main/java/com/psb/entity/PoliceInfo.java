package com.psb.entity;

/**
 * Created by aako on 2015/2/8.
 */
public class PoliceInfo {
    private int id;
    private int address_id;
    private int police_id;
    private PoliceAddr address;
    private Police police;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getPolice_id() {
        return police_id;
    }

    public void setPolice_id(int police_id) {
        this.police_id = police_id;
    }

    public PoliceAddr getAddress() {
        return address;
    }

    public void setAddress(PoliceAddr address) {
        this.address = address;
    }

    public Police getPolice() {
        return police;
    }

    public void setPolice(Police police) {
        this.police = police;
    }

    public class Police {

        private int id;
        private String police_name;
        private int police_station_id;
        private String phone;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPolice_name() {
            return police_name;
        }

        public void setPolice_name(String police_name) {
            this.police_name = police_name;
        }

        public int getPolice_station_id() {
            return police_station_id;
        }

        public void setPolice_station_id(int police_station_id) {
            this.police_station_id = police_station_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class PoliceAddr {

        private int id;
        private int parentid;
        private String name;
        private int order;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}