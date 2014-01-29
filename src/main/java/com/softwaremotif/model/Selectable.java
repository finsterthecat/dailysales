
package com.softwaremotif.model;

import javax.faces.model.SelectItem;
import javax.persistence.Transient;

/**
 *
 * Implement this to enable using model in JSF selects
 */
public interface Selectable {
    @Transient
    public SelectItem getSelectItem();
    
}
