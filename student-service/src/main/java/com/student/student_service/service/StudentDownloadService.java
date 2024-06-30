package com.student.student_service.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface StudentDownloadService {

	public ByteArrayInputStream getActualData() throws IOException;
}
