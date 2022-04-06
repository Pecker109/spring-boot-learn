package ${package};

import java.io.Serializable;
import lombok.Data;

/**
* <p>
    * ${tableComment}
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Data
public class ${classname} implements Serializable {


<#--????-->
<#list columns as pro>

    /**
    * ${pro.comment}
    */
    private ${pro.propertyType} ${pro.propertyName};
</#list>

}