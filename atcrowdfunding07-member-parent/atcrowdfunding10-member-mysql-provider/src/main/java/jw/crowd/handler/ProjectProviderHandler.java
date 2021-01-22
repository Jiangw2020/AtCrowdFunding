package jw.crowd.handler;

import jw.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectProviderHandler {
    @Autowired
    ProjectService projectService;
}
