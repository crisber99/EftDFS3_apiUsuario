#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.Exception;

public class ErrorResponse {
    private final String msg;

    public ErrorResponse(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
