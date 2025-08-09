package com.student.student_service.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@Validated
//@Slf4j
@RequestMapping("${base-uri.context}")
public interface TeacherController {

}
