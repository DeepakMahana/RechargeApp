package pooja_pawar.wipro.com.quadplay;

public class TransBean {

    private int id;
    private long CardNo;
    private String SerName;
    private long SerNum;
    private int amount;
    private String date;
    private String time;

    public TransBean(int id,long CardNo, String serName, long serNum, int amount, String date, String time ){
        this.id = id;
        this.CardNo = CardNo;
        this.SerName = serName;
        this.SerNum = serNum;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCardNo() {
        return CardNo;
    }

    public void setCardNo(long cardNo) {
        CardNo = cardNo;
    }

    public String getSerName() {
        return SerName;
    }

    public void setSerName(String serName) {
        SerName = serName;
    }

    public long getSerNum() {
        return SerNum;
    }

    public void setSerNum(long serNum) {
        SerNum = serNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
