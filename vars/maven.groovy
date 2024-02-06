def call(){
    node{
        env.APP_TYPE = "maven"
        common.lintchecks()
        sh "mvn clean compile"
        env.ARGS="-Dsonar.java.binaries=target/"
        common.sonarChecks()
    }
}


//the below pipeline is Declarative pipeline
//Call is the default function which will be called when you call the fileName
// def call(){
//     pipeline {
//         agent any
//         environment{
//             SONAR=credentials('SONAR_CREDS')
//             SONAR_URL="172.31.2.195"
//         }        
//         stages {
//             stage('Lint Checks') {
//                 steps {
//                     script{
//                         lintchecks()
//                     }      
//                 }                
//             }
//             stage('Sonar Checks') {
//                 steps {
//                     script {
//                         sh "mvn clean compile"
//                         env.ARGS="-Dsonar.java.binaries=target/"
//                         common.sonarChecks()
//                     }
//                 }
//             }
//             stage('test cases') {
//                 parallel {
//                     stage('Unit testing') {
//                         steps {
//                             //npm tset
//                             echo "Unit Testing Started"
//                             echo "Unit Testing Completed"                       
//                         }
//                     }
//                     stage('Integration testing') {
//                         steps {
//                             //npm verify
//                             echo "Integration Testing Started"
//                             echo "Integration Testing Completed"
//                         }
//                     }
//                     stage('Functional testing') {
//                         steps {
//                             echo "Functional Testing Started"
//                             echo "Functional Testing Started"
//                         }
//                     }
//                 }
//             }            
//         }
//     }
// }
