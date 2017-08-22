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

import fi.vm.kapa.identification.logging.Logger;
import fi.vm.kapa.identification.type.Identifier;
import fi.vm.kapa.identification.vtj.model.VTJResponse;
import fi.vm.kapa.identification.vtjclient.service.VTJPersonNotExistException;
import fi.vm.kapa.identification.vtjclient.service.VTJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Service
@Path("/vtj")
public class VTJResource {

    private static Logger log = Logger.getLogger(VTJResource.class, Logger.VTJ_CLIENT);

    private VTJService service;

    @Autowired
    public VTJResource(@Named("vtjService") VTJService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/person")
    public Response getPerson(
            @FormParam(value = "identifier") String identifier,
            @FormParam(value = "identifierType") Identifier.Types identifierType,
            @FormParam(value = "issuerDn") String issuerDn) {
        try {
            VTJResponse vtjResponse = service.getVTJResponse(identifier, identifierType, issuerDn);
            if (vtjResponse.isSuccess() && vtjResponse.getError() == null) {
                return Response.ok().entity(vtjResponse).build();
            } else {
                return Response.serverError().entity(vtjResponse).build();
            }
        } catch (VTJPersonNotExistException e) {
        	log.info(e.getMessage(), e);
        	return Response.status(Status.NOT_FOUND).build();
        } catch (Exception e) {
        	log.warning(e.getMessage(), e);
            return Response.serverError().build();
        }
    }
}