def lintchecks(COMPONENT){
    sh '''      
            echo Lint Checks for ${COMPONENT}
            echo installing jslint
            # npm install jslint
            # node_modules/jslint/bin/jslint.js server.js
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT} 
    '''
}

//Call is the default function which will be called when you call the fileName
def call(){
    pipeline {
        agent any
        environment{
            SONAR=credentials('SONAR_CREDS')
            SONAR_URL="172.31.2.195"
        } 
        stages {
            stage('Lint Checks') {
                steps {
                    script{
                        lintchecks()
                    }
                }
            }
            stage('sonar checks'){
                steps{
                    script{
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks()
                    }
                }
            }
            // stage('test cases') {
            //     parallel {
            //         stage('Unit testing') {
            //             steps {
            //                 //npm tset
            //                 echo "Unit Testing Started"
            //                 echo "Unit Testing Completed"                       
            //             }
            //         }
            //         stage('Integration testing') {
            //             steps {
            //                 //npm verify
            //                 echo "Integration Testing Started"
            //                 echo "Integration Testing Completed"
            //             }
            //         }
            //         stage('Functional testing') {
            //             steps {
            //                 echo "Functional Testing Started"
            //                 echo "Functional Testing Started"
            //             }
            //         }
            //     }
            // }
        }
    }
}

