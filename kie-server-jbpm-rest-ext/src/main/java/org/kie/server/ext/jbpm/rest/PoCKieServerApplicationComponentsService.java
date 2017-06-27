package org.kie.server.ext.jbpm.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.kie.server.services.api.KieServerApplicationComponentsService;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.api.SupportedTransports;
import org.jbpm.services.api.ProcessService;


public class PoCKieServerApplicationComponentsService implements KieServerApplicationComponentsService {

    private static final String OWNER_EXTENSION = "jBPM";
    
    public Collection<Object> getAppComponents(String extension, SupportedTransports type, Object... services) {
        // skip calls from other than owning extension
        if ( !OWNER_EXTENSION.equals(extension) ) {
            return Collections.emptyList();
        }
        
        ProcessService  processService = null;
        KieServerRegistry context = null;
       
        for( Object object : services ) { 
        	 if (object == null) {
                 continue;
             }
             if( ProcessService.class.isAssignableFrom(object.getClass()) ) {
                processService = (ProcessService) object;
                continue;
             } else if( KieServerRegistry.class.isAssignableFrom(object.getClass()) ) {
                 context = (KieServerRegistry) object;
                 continue;
             }
        }
        
        List<Object> components = new ArrayList<Object>(1);
        if( SupportedTransports.REST.equals(type) ) {
            components.add(new PoCResource(processService, context));
        }
        
        return components;
    }

}
