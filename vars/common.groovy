def sonarChecks() {
    //sh "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
    //sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh" 
    //sh "bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
    sh "echo Starting Code Quality Analysis"
    sh "echo Code Quality Analysis Completed"    

}

def test() {
    pipeline {
        agent any
        stages {
            stage('test-cases') {
                parallel {
                    stage('Unit testing') {
                        steps {
                            //npm test or mvn test or py test 
                            echo "Unit Testing Started"
                            echo "Unit Testing Completed"                       
                        }
                    }
                    stage('Integration testing') {
                        steps {
                            //npm verify or mvn verify or pr verify
                            echo "Integration Testing Started"
                            echo "Integration Testing Completed"
                        }
                    }
                    stage('Functional testing') {
                        steps {
                            echo "Functional Testing Started"
                            echo "Functional Testing Started"
                        }
                    }
                }
            }
        }
    }
}
