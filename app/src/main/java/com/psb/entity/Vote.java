package com.psb.entity;

/**
 * Created by zl on 2015/4/10.
 */
public class Vote {

    public static final String NO = "NO";

    private int id;
    private String vote_title;
    private Opt option;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVote_title() {
        return vote_title;
    }

    public void setVote_title(String vote_title) {
        this.vote_title = vote_title;
    }

    public Opt getOption() {
        return option;
    }

    public void setOption(Opt option) {
        this.option = option;
    }

    public class Opt {
        private String A;
        private String B;
        private String C;
        private String D;

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getC() {
            return C;
        }

        public void setC(String c) {
            C = c;
        }

        public String getD() {
            return D;
        }

        public void setD(String d) {
            D = d;
        }
    }

    public class Voted{

        private String voted;

        public String getVoted() {
            return voted;
        }

        public void setVoted(String voted) {
            this.voted = voted;
        }
    }
}




