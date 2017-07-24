'use strict';

describe('Controller Tests', function() {

    describe('BridSearch Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBridSearch, MockBridSearchRqst, MockBridSearchRsp;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBridSearch = jasmine.createSpy('MockBridSearch');
            MockBridSearchRqst = jasmine.createSpy('MockBridSearchRqst');
            MockBridSearchRsp = jasmine.createSpy('MockBridSearchRsp');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BridSearch': MockBridSearch,
                'BridSearchRqst': MockBridSearchRqst,
                'BridSearchRsp': MockBridSearchRsp
            };
            createController = function() {
                $injector.get('$controller')("BridSearchDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intgPocApp:bridSearchUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
