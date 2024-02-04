def lintchecks(COMPONENT){
    sh '''      
            echo Lint Checks for ${COMPONENT}
            # mvn checkstyle:check
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
            SONAR_URL="172.31.92.245"
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
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.sonarChecks()
                    }
                }
            }
            stage('test cases'){
                steps{
                    script{
                        common.test()
                    }
                }
            }            
        }
    }
}
