/**
 * The MIT License
 * Copyright (c) 2015 Population Register Centre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.vm.kapa.identification.vtjclient.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.vtj.model.*;

import org.springframework.stereotype.Service;

import fi.vm.kapa.identification.vtjclient.service.VTJService;

@Service
@Path("/vtj")
public class VTJResource {

    @Inject
    private VTJService service;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/person/{identifier}/{identifierType}")
    public Response getPerson(@PathParam("identifier") String identifier, @PathParam("identifierType") Identifier.Types identifierType, @QueryParam("issuerDn") String issuerDn) {
        try {
            VTJResponse vtjResponse = service.getVTJResponse(identifier, identifierType, issuerDn);
            if ( vtjResponse.isSuccess() && vtjResponse.getError() == null ) {
                return Response.ok().entity(vtjResponse).build();
            } else {
                return Response.serverError().entity(vtjResponse).build();
            }
        } catch (Exception e) { 
            ResponseBuilder responseBuilder = Response.serverError();
            return responseBuilder.build();
        }
    }
}