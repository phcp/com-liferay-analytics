/**
 * Bootstraps the basic message to LCS
 * @param {object} request Request object to alter
 * @param {object} analytics Analytics instance
 * @return {object} The updated request object
 */
function bootstrap(request, analytics) {
	const config = analytics.getConfig();
	const events = analytics.getEvents();

	const requestBody = {
		analyticsKey: config.analyticsKey,
		context: {},
		protocolVersion: '1.0',
		userId: config.userId,
		events,
	};

	return {...requestBody, ...request};
}

export {bootstrap};
export default bootstrap;
