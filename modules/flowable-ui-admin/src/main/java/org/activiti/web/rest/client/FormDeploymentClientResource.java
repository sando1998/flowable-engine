/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.web.rest.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.activiti.domain.EndpointType;
import org.activiti.domain.ServerConfig;
import org.activiti.service.engine.FormDeploymentService;
import org.activiti.service.engine.exception.ActivitiServiceException;
import org.activiti.web.rest.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Yvo Swillens
 */
@RestController
public class FormDeploymentClientResource extends AbstractClientResource {

	@Autowired
	protected FormDeploymentService clientService;

	@RequestMapping(value = "/rest/activiti/form-deployments/{deploymentId}", method = RequestMethod.GET, produces = "application/json")
	public JsonNode getDeployment(@PathVariable String deploymentId) throws BadRequestException {
		
		ServerConfig serverConfig = retrieveServerConfig(EndpointType.FORM);
		try {
			return clientService.getDeployment(serverConfig, deploymentId);
		} catch (ActivitiServiceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/rest/activiti/form-deployments/{deploymentId}", method = RequestMethod.DELETE)
	public void deleteDeployment(@PathVariable String deploymentId, HttpServletResponse httpResponse) {
	    clientService.deleteDeployment(retrieveServerConfig(EndpointType.FORM), httpResponse, deploymentId);
	}
}
