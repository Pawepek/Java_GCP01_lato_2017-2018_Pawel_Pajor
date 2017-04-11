public class CrawlerException extends  Exception {
    String message;
    public CrawlerException(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
}
