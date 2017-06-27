package org.kie.server.ext.jbpm.rest;

import static org.kie.server.remote.rest.common.util.RestUtils.createResponse;
import static org.kie.server.remote.rest.common.util.RestUtils.getContentType;
import static org.kie.server.remote.rest.common.util.RestUtils.getVariant;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jbpm.services.api.ProcessService;
import org.kie.server.remote.rest.common.Header;


import static org.kie.server.api.rest.RestURI.*;
import static org.kie.server.remote.rest.common.util.RestUtils.*;

@Path("server/containers/{id}/pocres")
public class PoCResource {

    private static final Logger logger = LoggerFactory.getLogger(PoCResource.class);
    
    private KieCommands commandsFactory = KieServices.Factory.get().getCommands();

    private ProcessService processService;
    private KieServerRegistry registry;

    public PoCResource() {
    	logger.info("No arg init");

    }

    public PoCResource(ProcessService processService, KieServerRegistry registry) {
    	logger.info("Arg init");
        this.processService = processService;
        this.registry = registry;
    }
    
    @GET
    @Path("{PoCId}/echo")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response echoProcess(@Context HttpHeaders headers, @PathParam("id") String containerId, @PathParam("PoCId") String PoCId, @DefaultValue("") String payload) {
        Variant v = getVariant(headers);
        String type = getContentType(headers);
        String response = null;
        try {
           // String response = processServiceBase.startProcess(containerId, processId, payload, type);
        	response = "Echo for container: "+ containerId + ", PoCResource: "+ PoCId;
            logger.info("Returning CREATED response with content '{}'", response);
            Header conversationIdHeader = buildConversationIdHeader(containerId, registry, headers);
            return createResponse(response, v, Response.Status.OK, conversationIdHeader);
        } catch (Exception e) {
            logger.error("Unexpected error during processing {}", e.getMessage(), e);
            return createResponse(response, v, Response.Status.INTERNAL_SERVER_ERROR);
        }      
        
    }
}    
