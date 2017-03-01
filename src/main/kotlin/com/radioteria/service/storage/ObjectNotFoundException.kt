package com.radioteria.service.storage

class ObjectNotFoundException(objectKey: String) : Exception("Object $objectKey does not exit.")
