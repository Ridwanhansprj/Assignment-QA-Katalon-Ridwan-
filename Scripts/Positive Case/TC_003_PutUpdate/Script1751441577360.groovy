import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper

KeywordUtil.logInfo("Memulai Tes: PUT Update User dengan Verifikasi Lengkap")

'Siapkan Data yang mau dikirim'
def nameToSend = "Ridwan Han"
def jobToSend = "qa engineer"
KeywordUtil.logInfo("Data yang akan di-update: Nama='${nameToSend}', Pekerjaan='${jobToSend}'")

'Kirim Request dengan Data Dinamis'
// Mengisi placeholder 'updatedName' dan 'updatedJob' di dalam Object Request.
def response = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/PUT_update', [
    ('updatedName') : nameToSend, 
    ('updatedJob')  : jobToSend
]))

'Check Status & Header'
KeywordUtil.logInfo("Memverifikasi status code dan header...")
WS.verifyResponseStatusCode(response, 200)
String contentTypeHeader = response.getHeaderField('Content-Type')
assert contentTypeHeader.contains('application/json')
KeywordUtil.logInfo("Status Code dan Content-Type sudah sesuai.")

'Parse JSON'
def jsonSlurper = new JsonSlurper()
def parsedResponse = jsonSlurper.parseText(response.getResponseText())

'Verifikasi Field updatedAt'
// Memastikan field 'updatedAt' ada dan berisi data.
assert parsedResponse.containsKey('updatedAt') : "Respons harus mengandung field 'updatedAt'"
assert parsedResponse.updatedAt instanceof String && !(parsedResponse.updatedAt.isEmpty()) : "Nilai 'updatedAt' harus berupa String dan tidak boleh kosong"
KeywordUtil.logInfo("Field 'updatedAt' ada dan valid. Nilai: ${parsedResponse.updatedAt}")

KeywordUtil.logInfo("Tes Selesai: Verifikasi PUT Update berhasil.")